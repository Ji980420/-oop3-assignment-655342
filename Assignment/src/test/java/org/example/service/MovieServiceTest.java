package org.example.service;

import org.example.client.OmdbClient;
import org.example.client.TmdbClient;
import org.example.entity.MovieEntity;
import org.example.model.MovieDTO;
import org.example.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class MovieServiceTest {

    @Test
    void addMovie_parallelFetchAndStreams() {
        OmdbClient omdbClient = Mockito.mock(OmdbClient.class, Mockito.withSettings().serializable());
        TmdbClient tmdbClient = Mockito.mock(TmdbClient.class, Mockito.withSettings().serializable());
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);

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

        MovieService service = new MovieService(omdbClient, tmdbClient, movieRepository);

        MovieEntity entity = service.addMovie("Inception");

        assertEquals("Inception", entity.getTitle());
        assertEquals("2010", entity.getReleaseYear());
        assertEquals("Christopher Nolan", entity.getDirector());
        assertEquals("Sci-Fi", entity.getGenre());
        assertEquals(3, entity.getImagePaths().size());
        assertTrue(entity.getSimilarMovieTitles().contains("Interstellar"));
        assertTrue(entity.getSimilarMovieTitles().contains("Memento"));
    }

    private List<String> processSimilarMovies(List<String> similarMovies) {
        return similarMovies.parallelStream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}