package org.example.controller;

import org.example.entity.MovieEntity;
import org.example.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping("/{title}")
    public MovieEntity addMovie(@PathVariable String title) {
        return service.addMovie(title);
    }

    @GetMapping
    public List<MovieEntity> getMovies(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getMovies(page, size);
    }

    @PatchMapping("/{id}/watched")
    public void updateWatched(@PathVariable Long id, @RequestParam boolean watched) {
        service.setWatched(id, watched);
    }

    @PatchMapping("/{id}/rating")
    public void updateRating(@PathVariable Long id, @RequestParam int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        service.setRating(id, rating);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        service.deleteMovie(id);
    }
}
