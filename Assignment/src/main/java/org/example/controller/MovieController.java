// controller/MovieController.java
package org.example.controller;

import org.example.model.MovieDTO;
import org.example.service.MovieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{title}")
    public MovieDTO getMovieData(@PathVariable String title) {
        return movieService.fetchFullMovieData(title);
    }
}
