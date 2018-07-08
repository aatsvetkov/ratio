package com.ratiose.testtask.service;

import com.ratiose.testtask.entity.User;

public interface UserService {
    User registerUser(String email, String password);

    String getCurrentUserUsername();

    User getCurrentUser();
}

