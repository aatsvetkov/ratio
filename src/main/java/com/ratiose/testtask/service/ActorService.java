package com.ratiose.testtask.service;

import com.ratiose.testtask.entity.User;

public interface ActorService {
    void addFavorite(String actorId, String username);
    void removeFavorite(String actorId, String username);
}
