package com.ratiose.testtask.service.impl;

import com.ratiose.testtask.dto.MovieDto;
import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.repository.UserRepository;
import com.ratiose.testtask.service.MovieService;
import com.ratiose.testtask.service.UserService;
import com.ratiose.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TmdbApi tmdbApi;

    @Override
    public void markWatched(Integer movieId, String username) {
        User user = userRepository.findByEmail(username);
        user.getWatchedMoviesIds().add(movieId);
        userRepository.save(user);
    }

    @Override
    public List<MovieDto> findFavoriteUnwatched(String year, String month) {
        return tmdbApi.getUnwatchedMovies(userService.getCurrentUser().getFavoriteActorsIds(), year, month);
    }
}
