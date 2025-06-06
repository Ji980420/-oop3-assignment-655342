// client/OmdbClient.java
package org.example.client;

import org.example.model.MovieDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Component
public class OmdbClient {

    @Value("${omdb.api.key}")
    private String omdbApiKey;

    public MovieDTO getBasicMovieInfo(String title) {
        String url = String.format("http://www.omdbapi.com/?t=%s&apikey=%s", title, omdbApiKey);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        MovieDTO dto = new MovieDTO();
        dto.setTitle(json.optString("Title"));
        dto.setYear(json.optString("Year"));
        dto.setDirector(json.optString("Director"));
        dto.setGenre(json.optString("Genre"));
        return dto;
    }
}
