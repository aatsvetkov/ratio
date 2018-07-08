package com.ratiose.testtask.controller;

import com.ratiose.testtask.service.ActorService;
import com.ratiose.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actor")
public class ActorController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActorService actorService;

    @Secured("ROLE_USER")
    @PostMapping("/favorite/{actorId}")
    public void addFavorite(@PathVariable String actorId) {
        actorService.addFavorite(actorId, userService.getCurrentUserUsername());
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/favorite/{actorId}")
    public void removeFavorite(@PathVariable String actorId) {
        actorService.removeFavorite(actorId, userService.getCurrentUserUsername());
    }
}

