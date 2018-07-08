package com.ratiose.testtask.service.impl;

import com.ratiose.testtask.entity.User;
import com.ratiose.testtask.repository.UserRepository;
import com.ratiose.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return null;
        }
        user = createUser(email, password);
        return userRepository.save(user);
    }

    private User createUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

    @Override
    public String getCurrentUserUsername() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    @Override
    public User getCurrentUser(){
        return userRepository.findByEmail(getCurrentUserUsername());
    }
}