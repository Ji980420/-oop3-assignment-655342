// client/TmdbClient.java
package org.example.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Component
public class TmdbClient {

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    private final String TMDB_SEARCH_URL = "https://api.themoviedb.org/3/search/movie";
    private final String TMDB_SIMILAR_URL = "https://api.themoviedb.org/3/movie/%s/similar";
    private final String TMDB_IMAGES_URL = "https://api.themoviedb.org/3/movie/%s/images";

    public List<String> getImageUrls(String title) {
        String movieId = getMovieIdByTitle(title);
        String url = String.format(TMDB_IMAGES_URL + "?api_key=%s", movieId, tmdbApiKey);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        JSONArray backdrops = json.getJSONArray("backdrops");

        List<String> images = new ArrayList<>();
        for (int i = 0; i < Math.min(3, backdrops.length()); i++) {
            String path = backdrops.getJSONObject(i).getString("file_path");
            images.add("https://image.tmdb.org/t/p/w780" + path);
        }
        return images;
    }

    public List<String> getSimilarMovies(String title) {
        String movieId = getMovieIdByTitle(title);
        String url = String.format(TMDB_SIMILAR_URL + "?api_key=%s", movieId, tmdbApiKey);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        JSONArray results = json.getJSONArray("results");

        List<String> similar = new ArrayList<>();
        for (int i = 0; i < Math.min(5, results.length()); i++) {
            similar.add(results.getJSONObject(i).getString("title"));
        }
        return similar;
    }

    private String getMovieIdByTitle(String title) {
        String url = TMDB_SEARCH_URL + "?api_key=" + tmdbApiKey + "&query=" + title.replace(" ", "%20");
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(response);
        JSONArray results = json.getJSONArray("results");
        return results.getJSONObject(0).get("id").toString();
    }
}
