// client/TmdbClient.java
package org.example.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TmdbClient {

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    private final String TMDB_SEARCH_URL = "https://api.themoviedb.org/3/search/movie";
    private final String TMDB_SIMILAR_URL = "https://api.themoviedb.org/3/movie/%s/similar";
    private final String TMDB_IMAGES_URL = "https://api.themoviedb.org/3/movie/%s/images";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<String> getImageUrls(String title) {
        String movieId = getMovieIdByTitle(title);
        String url = String.format(TMDB_IMAGES_URL + "?api_key=%s", movieId, tmdbApiKey);

        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(response);
        JSONArray backdrops = json.optJSONArray("backdrops");

        List<String> images = new ArrayList<>();
        if (backdrops != null) {
            for (int i = 0; i < Math.min(3, backdrops.length()); i++) {
                String path = backdrops.getJSONObject(i).getString("file_path");
                images.add("https://image.tmdb.org/t/p/w780" + path);
            }
        }
        return images;
    }

    public List<String> getSimilarMovies(String title) {
        String movieId = getMovieIdByTitle(title);
        String url = String.format(TMDB_SIMILAR_URL + "?api_key=%s", movieId, tmdbApiKey);

        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(response);
        JSONArray results = json.optJSONArray("results");

        List<String> similar = new ArrayList<>();
        if (results != null) {
            for (int i = 0; i < Math.min(5, results.length()); i++) {
                similar.add(results.getJSONObject(i).getString("title"));
            }
        }
        return similar;
    }

    private String getMovieIdByTitle(String title) {
        String url = TMDB_SEARCH_URL + "?api_key=" + tmdbApiKey + "&query=" + title.replace(" ", "%20");
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        JSONArray results = json.optJSONArray("results");

        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Movie not found in TMDB: " + title);
        }

        return results.getJSONObject(0).get("id").toString();
    }

    /**
     * Downloads top 3 images for a movie and saves them to the file system.
     * 
     * @param title Movie title
     * @return List of saved image paths (relative to static/)
     */
    public List<String> downloadImages(String title) {
        List<String> imageUrls = getImageUrls(title);
        List<String> savedPaths = new ArrayList<>();

        String baseFolder = "src/main/resources/static/images";
        String safeTitle = title.replaceAll("\\s+", "_");

        try {
            Files.createDirectories(Paths.get(baseFolder));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create image directory", e);
        }

        for (int i = 0; i < imageUrls.size(); i++) {
            String imageUrl = imageUrls.get(i);
            String fileName = safeTitle + "_img" + i + ".jpg";
            Path savePath = Paths.get(baseFolder, fileName);

            try (InputStream in = new URL(imageUrl).openStream()) {
                Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
                savedPaths.add("images/" + fileName); // Relative to /static
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return savedPaths;
    }
}