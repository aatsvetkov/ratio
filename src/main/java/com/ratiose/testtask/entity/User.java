package com.ratiose.testtask.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @ElementCollection
    private Set<String> favoriteActorsIds = new HashSet<>();
    @ElementCollection
    private Set<Integer> watchedMoviesIds = new HashSet<>();

    public Set<String> getFavoriteActorsIds() {
        return favoriteActorsIds;
    }

    public void setFavoriteActorsIds(Set<String> favoriteActorsIds) {
        this.favoriteActorsIds = favoriteActorsIds;
    }

    public Set<Integer> getWatchedMoviesIds() {
        return watchedMoviesIds;
    }

    public void setWatchedMoviesIds(Set<Integer> watchedMoviesIds) {
        this.watchedMoviesIds = watchedMoviesIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}