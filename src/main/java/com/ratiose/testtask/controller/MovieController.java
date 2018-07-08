package com.ratiose.testtask.controller;

import com.ratiose.testtask.service.MovieService;
import com.ratiose.testtask.service.UserService;
import com.ratiose.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private TmdbApi tmdbApi;

    @Secured("ROLE_USER")
    @PostMapping("/popular")
    public ResponseEntity popular() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(tmdbApi.popularMovies());
    }

    @Secured("ROLE_USER")
    @PostMapping("/watched/{movieId}")
    public void markWatched(@PathVariable int movieId) {
        movieService.markWatched(movieId, userService.getCurrentUserUsername());
    }

    @Secured("ROLE_USER")
    @GetMapping("/searchByFavoriteActorsUnwatchedMovies")
    public ResponseEntity searchMovies(@RequestParam String year, @RequestParam String month) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(movieService.findFavoriteUnwatched(year, month));
    }

}
