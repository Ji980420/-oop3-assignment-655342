package org.example.model;

import java.util.List;

public class MovieDTO {
    private String title;
    private String releaseYear;
    private String director;
    private String genre;
    private Long id;
    private List<String> imagePaths; // Add this if you want to match entity, or change controller to use imageUrls
    private boolean watched;
    private int rating;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
                ", id=" + id +
                ", imagePaths=" + imagePaths +
                ", watched=" + watched +
                ", rating=" + rating +
                ", similarMovieTitles=" + similarMovieTitles +
                '}';
    }
}
