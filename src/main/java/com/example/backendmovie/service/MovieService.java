package com.example.backendmovie.service;

import com.example.backendmovie.entity.Movie;
import com.example.backendmovie.repository.MovieRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * Service class to manage movie-related operations.
 */
@Service
public class MovieService {


    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Adds a new movie to the repository.
    @CacheEvict(value = "movies", allEntries = true)
    public Movie addMovie(Movie movie) {
        if (movieRepository.findByNameAndReleaseYear(movie.getName(), movie.getReleaseYear()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie with the same name and release year already exists.");
        }
        return movieRepository.save(movie);
    }

    // Deletes a movie by its ID.
    @CacheEvict(value = "movies", allEntries = true)
    public void deleteMovie(UUID id) {
        movieRepository.deleteById(id);
    }

    // Retrieves all movies.
    @Cacheable(value = "movies")
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    // Updates the details of an existing movie.
    @CacheEvict(value = "movies", allEntries = true)
    public Movie updateMovie(Movie movie) {
        if (!movieRepository.existsById(movie.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        return movieRepository.save(movie);
    }

    // Retrieves distinct release years for movies.
    public List<Integer> getDistinctReleaseYears() {
        return movieRepository.findDistinctReleaseYears();
    }

    // Gets movies based on their release year.
    public List<Movie> getMoviesByReleaseYear(Integer year, Pageable pageable) {
        Page<Movie> page = movieRepository.findByReleaseYear(year, pageable);
        return page.getContent();
    }

    // Checks if a movie exists by its ID.
    public boolean existsById(UUID id) {
        return movieRepository.existsById(id);
    }
}