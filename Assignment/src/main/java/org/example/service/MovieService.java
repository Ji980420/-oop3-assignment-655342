// service/MovieService.java
package org.example.service;

import org.example.client.OmdbClient;
import org.example.client.TmdbClient;
import org.example.model.MovieDTO;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final OmdbClient omdbClient;
    private final TmdbClient tmdbClient;

    public MovieService(OmdbClient omdbClient, TmdbClient tmdbClient) {
        this.omdbClient = omdbClient;
        this.tmdbClient = tmdbClient;
    }

    public MovieDTO fetchFullMovieData(String title) {
        MovieDTO movie = omdbClient.getBasicMovieInfo(title);
        movie.setImageUrls(tmdbClient.getImageUrls(title));
        movie.setSimilarMovieTitles(tmdbClient.getSimilarMovies(title));
        return movie;
    }
}
