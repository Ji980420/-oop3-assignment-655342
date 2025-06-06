// App.java
package org.example;

import org.example.client.OmdbClient;
import org.example.client.TmdbClient;
import org.example.model.MovieDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);

        OmdbClient omdb = context.getBean(OmdbClient.class);
        TmdbClient tmdb = context.getBean(TmdbClient.class);

        String title = "Inception";

        MovieDTO movie = omdb.getBasicMovieInfo(title);
        movie.setImageUrls(tmdb.getImageUrls(title));
        movie.setSimilarMovieTitles(tmdb.getSimilarMovies(title));

        System.out.println(movie);
    }
}
