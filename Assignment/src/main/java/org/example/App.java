// App.java
package org.example;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
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

        // Fetch OMDb and TMDb data in parallel
        CompletableFuture<MovieDTO> omdbFuture = CompletableFuture.supplyAsync(() -> omdb.getBasicMovieInfo(title));
        CompletableFuture<List<String>> imageUrlsFuture = CompletableFuture.supplyAsync(() -> tmdb.getImageUrls(title));
        CompletableFuture<List<String>> similarMoviesFuture = CompletableFuture.supplyAsync(() -> tmdb.getSimilarMovies(title));

        try {
            MovieDTO movie = omdbFuture.get();
            List<String> imageUrls = imageUrlsFuture.get();
            List<String> similarMovies = similarMoviesFuture.get();

            // Download images in parallel using Stream API (if you have a download method)
            List<String> imagePaths = imageUrls.parallelStream()
                .map(url -> tmdb.downloadSingleImage(title, url))
                .collect(Collectors.toList());

            movie.setImagePaths(imagePaths);
            movie.setSimilarMovieTitles(similarMovies);

            System.out.println(movie);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
