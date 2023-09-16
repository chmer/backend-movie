package com.example.backendmovie.entity;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

/**
 * Represents a Movie entity with attributes like name, release year, and rating.
 */
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Min(value = 0, message = "Release year should not be negative")
    @Max(value = 9999, message = "Release year should not exceed 4 digits")
    private int releaseYear;

    @NotNull
    @Min(value = 1, message = "Rating value should be between 1 and 3")
    @Max(value = 3, message = "Rating value should be between 1 and 3")
    private int rating;

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
