package org.example.model;

import java.util.List;

public class MovieDTO {
    private String title;
    private String releaseYear;
    private String director;
    private String genre;

    // Add these two for TMDB
    private List<String> imageUrls;
    private List<String> similarMovieTitles;

    // --- Getters and Setters ---
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getSimilarMovieTitles() {
        return similarMovieTitles;
    }

    public void setSimilarMovieTitles(List<String> similarMovieTitles) {
        this.similarMovieTitles = similarMovieTitles;
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "title='" + title + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", imageUrls=" + imageUrls +
                ", similarMovieTitles=" + similarMovieTitles +
                '}';
    }
}
