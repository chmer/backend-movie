package com.example.backendmovie.service;

import com.example.backendmovie.entity.Movie;
import com.example.backendmovie.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    public void whenAddingMovie_thenShouldSaveAndReturnMovie() {
        // Arrange
        Movie movie = new Movie();
        movie.setName("Test Movie");
        movie.setReleaseYear(2022);
        movie.setRating(3);

        when(movieRepository.findByNameAndReleaseYear(movie.getName(), movie.getReleaseYear()))
                .thenReturn(Optional.empty());
        when(movieRepository.save(movie)).thenReturn(movie);

        // Act
        Movie savedMovie = movieService.addMovie(movie);

        // Assert
        assertEquals(savedMovie.getName(), movie.getName());
    }

    @Test
    public void whenDeletingMovie_thenShouldInvokeRepositoryDelete() {
        // Arrange
        UUID id = UUID.randomUUID();
        doNothing().when(movieRepository).deleteById(id);

        // Act
        movieService.deleteMovie(id);

        // Assert
        verify(movieRepository, times(1)).deleteById(id);
    }
}
