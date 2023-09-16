package com.example.backendmovie.repository;

import com.example.backendmovie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Movie-related data operations.
 */
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    //Retrieves a paginated list of movies by their release year.
    Page<Movie> findByReleaseYear(Integer releaseYear, Pageable pageable);


    //Finds a movie based on its name and release year.
    Optional<Movie> findByNameAndReleaseYear(String name, Integer releaseYear);


    //Retrieves a list of distinct release years for all movies.
    @Query("SELECT DISTINCT m.releaseYear FROM Movie m")
    List<Integer> findDistinctReleaseYears();
}
