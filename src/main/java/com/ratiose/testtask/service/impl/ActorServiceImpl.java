package com.ratiose.testtask.service.impl;

import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.repository.UserRepository;
import com.ratiose.testtask.service.ActorService;
import com.ratiose.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements ActorService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public void addFavorite(String actorId, String username) {
        User user = userRepository.findByEmail(username);
        user.getFavoriteActorsIds().add(actorId);
        userRepository.save(user);
    }

    @Override
    public void removeFavorite(String actorId, String username) {
        User user = userRepository.findByEmail(username);
        user.getFavoriteActorsIds().remove(actorId);
        userRepository.save(user);
    }

}
