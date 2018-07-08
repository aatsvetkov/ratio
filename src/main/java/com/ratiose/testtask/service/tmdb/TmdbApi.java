package com.ratiose.testtask.service.tmdb;

import com.ratiose.testtask.dto.MovieDto;

import java.util.List;
import java.util.Set;

public interface TmdbApi {
    String popularMovies();

    List<MovieDto> getUnwatchedMovies(Set<String> actorsIds, String year, String month);
}
