package org.example.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data

public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String releaseYear;
    private String director;
    private String genre;

    @ElementCollection
    private List<String> imagePaths;

    @ElementCollection
    private List<String> similarMovieTitles;

    private boolean watched;
    private int rating;

    public MovieEntity(Long id, String title, String releaseYear, String director, String genre,
            List<String> imagePaths, List<String> similarMovieTitles, boolean watched, int rating) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.genre = genre;
        this.imagePaths = imagePaths;
        this.similarMovieTitles = similarMovieTitles;
        this.watched = watched;
        this.rating = rating;
    }

    public MovieEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public List<String> getSimilarMovieTitles() {
        return similarMovieTitles;
    }

    public void setSimilarMovieTitles(List<String> similarMovieTitles) {
        this.similarMovieTitles = similarMovieTitles;
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
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "MovieEntity [id=" + id + ", title=" + title + ", releaseYear=" + releaseYear + ", director=" + director
                + ", genre=" + genre + ", imagePaths=" + imagePaths + ", similarMovieTitles=" + similarMovieTitles
                + ", watched=" + watched + ", rating=" + rating + ", getDirector()=" + getDirector() + ", getGenre()="
                + getGenre() + ", getId()=" + getId() + ", getImagePaths()=" + getImagePaths() + ", getRating()="
                + getRating() + ", getReleaseYear()=" + getReleaseYear() + ", getTitle()=" + getTitle()
                + ", hashCode()="
                + hashCode() + ", isWatched()=" + isWatched() + ", getClass()=" + getClass() + ", toString()="
                + super.toString() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MovieEntity other = (MovieEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (releaseYear == null) {
            if (other.releaseYear != null)
                return false;
        } else if (!releaseYear.equals(other.releaseYear))
            return false;
        if (director == null) {
            if (other.director != null)
                return false;
        } else if (!director.equals(other.director))
            return false;
        if (genre == null) {
            if (other.genre != null)
                return false;
        } else if (!genre.equals(other.genre))
            return false;
        if (imagePaths == null) {
            if (other.imagePaths != null)
                return false;
        } else if (!imagePaths.equals(other.imagePaths))
            return false;
        if (similarMovieTitles == null) {
            if (other.similarMovieTitles != null)
                return false;
        } else if (!similarMovieTitles.equals(other.similarMovieTitles))
            return false;
        if (watched != other.watched)
            return false;
        if (rating != other.rating)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((releaseYear == null) ? 0 : releaseYear.hashCode());
        result = prime * result + ((director == null) ? 0 : director.hashCode());
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((imagePaths == null) ? 0 : imagePaths.hashCode());
        result = prime * result + ((similarMovieTitles == null) ? 0 : similarMovieTitles.hashCode());
        result = prime * result + (watched ? 1231 : 1237);
        result = prime * result + rating;
        return result;
    }
}
