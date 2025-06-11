package org.example.service;

import org.example.client.OmdbClient;
import org.example.client.TmdbClient;
import org.example.entity.MovieEntity;
import org.example.model.MovieDTO;
import org.example.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final OmdbClient omdbClient;
    private final TmdbClient tmdbClient;
    private final MovieRepository movieRepository;

    public MovieService(OmdbClient omdbClient, TmdbClient tmdbClient, MovieRepository movieRepository) {
        this.omdbClient = omdbClient;
        this.tmdbClient = tmdbClient;
        this.movieRepository = movieRepository;
    }

    public MovieEntity addMovie(String title) {
        MovieDTO dto = omdbClient.getBasicMovieInfo(title);
        List<String> imagePaths = tmdbClient.downloadImages(title);

        MovieEntity movie = new MovieEntity();
        movie.setTitle(dto.getTitle());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDirector(dto.getDirector());
        movie.setGenre(dto.getGenre());
        movie.setImagePaths(imagePaths);
        movie.setWatched(false);
        movie.setRating(1);

        return movieRepository.save(movie);
    }

    public List<MovieEntity> getMovies(int page, int size) {
        return movieRepository.findAll(org.springframework.data.domain.PageRequest.of(page, size)).getContent();
    }

    public void setWatched(Long id, boolean watched) {
        movieRepository.findById(id).ifPresent(m -> {
            m.setWatched(watched);
            movieRepository.save(m);
        });
    }

    public void setRating(Long id, int rating) {
        movieRepository.findById(id).ifPresent(m -> {
            m.setRating(rating);
            movieRepository.save(m);
        });
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
