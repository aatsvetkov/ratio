package com.ratiose.testtask.service;

import com.ratiose.testtask.dto.MovieDto;

import java.util.List;

public interface MovieService {
    void markWatched(Integer movieId, String username);

    List<MovieDto> findFavoriteUnwatched(String year, String month);
}
