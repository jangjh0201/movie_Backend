package com.dodatabase.movie_backend.controller;

import com.dodatabase.movie_backend.domain.Movie.Movie;
import com.dodatabase.movie_backend.domain.Movie.MovieResponseItem;
import com.dodatabase.movie_backend.service.MovieService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class JpaController {

    private final MovieService movieService;

    private final ModelMapper modelMapper;

    @PostMapping("/api/new")
    public ResponseEntity<Movie> addMovies(@RequestBody MovieResponseItem item) {
        Optional<Movie> byTitle = movieService.findByTitle(item.getTitle());

        try {
            if (byTitle.isPresent()) {
                throw new Exception("이미 존재하는 영화입니다.");
            } else {
                movieService.create(modelMapper.map(item, Movie.class));
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/movies/delete")
    public void removeMovies(@RequestBody MovieResponseItem item) {
        movieService.deleteByTitle(item.getTitle());
    }
}