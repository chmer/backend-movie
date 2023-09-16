package com.example.backendmovie.controller;

import com.example.backendmovie.entity.Movie;
import com.example.backendmovie.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Controller class for managing movie-related operations.
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Handle constraint violations, especially for validation purposes.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Add a new movie to the database.
     */
    @PostMapping
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie) {
        Movie savedMovie = movieService.addMovie(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    /**
     * Delete a movie by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieve all movies with pagination support.
     */
    @GetMapping
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieService.getAllMovies(pageable);
    }

    /**
     * Update movie details.
     */
    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable UUID id, @RequestBody Movie movie) {
        if (movieService.existsById(id)) {
            movie.setId(id);
            return movieService.updateMovie(movie);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

    /**
     * Retrieve distinct release years of movies.
     */
    @GetMapping("/years")
    public List<Integer> getDistinctReleaseYears() {
        return movieService.getDistinctReleaseYears();
    }

    /**
     * Retrieve movies by their release year with pagination support.
     */
    @GetMapping("/year/{year}")
    public List<Movie> getMoviesByReleaseYear(@PathVariable Integer year,
                                              @PageableDefault(size = 10) Pageable pageable) {
        return movieService.getMoviesByReleaseYear(year, pageable);
    }
}
