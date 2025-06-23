package org.example.service;

import org.example.client.OmdbClient;
import org.example.client.TmdbClient;
import org.example.entity.MovieEntity;
import org.example.model.MovieDTO;
import org.example.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
        // Fetch OMDb and TMDb data in parallel
        CompletableFuture<MovieDTO> omdbFuture = CompletableFuture
                .supplyAsync(() -> omdbClient.getBasicMovieInfo(title));
        CompletableFuture<List<String>> imageUrlsFuture = CompletableFuture
                .supplyAsync(() -> tmdbClient.getImageUrls(title));
        CompletableFuture<List<String>> similarMoviesFuture = CompletableFuture
                .supplyAsync(() -> tmdbClient.getSimilarMovies(title));

        try {
            MovieDTO dto = omdbFuture.get();
            List<String> imageUrls = imageUrlsFuture.get();
            List<String> similarMovies = similarMoviesFuture.get();

            // Download images in parallel using Stream API
            List<String> imagePaths = imageUrls.parallelStream()
                    .map(url -> tmdbClient.downloadSingleImage(title, url))
                    .collect(Collectors.toList());

            MovieEntity movie = new MovieEntity();
            movie.setTitle(dto.getTitle());
            movie.setReleaseYear(dto.getReleaseYear());
            movie.setDirector(dto.getDirector());
            movie.setGenre(dto.getGenre());
            movie.setImagePaths(imagePaths);
            movie.setSimilarMovieTitles(similarMovies);
            movie.setWatched(false);
            movie.setRating(1);

            return movieRepository.save(movie);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to fetch movie data", e);
        }
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
