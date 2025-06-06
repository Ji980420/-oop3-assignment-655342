package org.example.model;

import java.util.List;

public class MovieDTO {
    private String title;
    private String year;
    private String director;
    private String genre;

    // Add these two for TMDB
    private List<String> imageUrls;
    private List<String> similarMovieTitles;

    // --- Getters and Setters ---
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public List<String> getSimilarMovieTitles() { return similarMovieTitles; }
    public void setSimilarMovieTitles(List<String> similarMovieTitles) {
        this.similarMovieTitles = similarMovieTitles;
    }
}
