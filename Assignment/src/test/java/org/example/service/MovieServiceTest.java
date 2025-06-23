package org.example.service;

import org.example.client.OmdbClient;
import org.example.client.TmdbClient;
import org.example.entity.MovieEntity;
import org.example.model.MovieDTO;
import org.example.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class MovieServiceTest {

    private OmdbClient omdbClient;
    private TmdbClient tmdbClient;
    private MovieRepository movieRepository;
    private MovieService service;

    @BeforeEach
    void setUp() {
        omdbClient = Mockito.mock(OmdbClient.class);
        tmdbClient = Mockito.mock(TmdbClient.class);
        movieRepository = Mockito.mock(MovieRepository.class);
        service = new MovieService(omdbClient, tmdbClient, movieRepository);
    }

    @Test
    void addMovie_parallelFetchAndStreams() {
        MovieDTO dto = new MovieDTO();
        dto.setTitle("Inception");
        dto.setReleaseYear("2010");
        dto.setDirector("Christopher Nolan");
        dto.setGenre("Sci-Fi");

        Mockito.when(omdbClient.getBasicMovieInfo(any())).thenReturn(dto);
        Mockito.when(tmdbClient.getSimilarMovies(any())).thenReturn(Arrays.asList("Interstellar", "Memento"));
        Mockito.when(tmdbClient.getImageUrls(any())).thenReturn(Arrays.asList("url1", "url2", "url3"));
        Mockito.when(tmdbClient.downloadSingleImage(any(), any()))
                .thenAnswer(invocation -> "images/" + invocation.getArgument(1).hashCode() + ".jpg");
        Mockito.when(movieRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        MovieEntity entity = service.addMovie("Inception");

        assertEquals("Inception", entity.getTitle());
        assertEquals("2010", entity.getReleaseYear());
        assertEquals("Christopher Nolan", entity.getDirector());
        assertEquals("Sci-Fi", entity.getGenre());
        assertEquals(3, entity.getImagePaths().size());
        assertTrue(entity.getSimilarMovieTitles().contains("Interstellar"));
        assertTrue(entity.getSimilarMovieTitles().contains("Memento"));
    }

    @Test
    void getMovies_returnsPagedMovies() {
        MovieEntity movie1 = new MovieEntity();
        movie1.setTitle("Movie 1");
        MovieEntity movie2 = new MovieEntity();
        movie2.setTitle("Movie 2");
        List<MovieEntity> movies = Arrays.asList(movie1, movie2);

        Page<MovieEntity> page = new PageImpl<>(movies);

        Mockito.when(movieRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        List<MovieEntity> result = service.getMovies(0, 2);
        assertEquals(2, result.size());
        assertEquals("Movie 1", result.get(0).getTitle());
        assertEquals("Movie 2", result.get(1).getTitle());
    }

    @Test
    void setWatched_updatesWatchedStatus() {
        MovieEntity movie = new MovieEntity();
        movie.setWatched(false);

        Mockito.when(movieRepository.findById(1L)).thenReturn(java.util.Optional.of(movie));
        Mockito.when(movieRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.setWatched(1L, true);

        assertTrue(movie.isWatched());
    }

    @Test
    void setRating_updatesRating() {
        MovieEntity movie = new MovieEntity();
        movie.setRating(1);

        Mockito.when(movieRepository.findById(1L)).thenReturn(java.util.Optional.of(movie));
        Mockito.when(movieRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.setRating(1L, 5);

        assertEquals(5, movie.getRating());
    }

    @Test
    void deleteMovie_removesMovie() {
        service.deleteMovie(1L);
        Mockito.verify(movieRepository).deleteById(1L);
    }

    private List<String> processSimilarMovies(List<String> similarMovies) {
        return similarMovies.parallelStream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}