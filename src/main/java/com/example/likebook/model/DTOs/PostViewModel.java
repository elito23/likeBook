package com.example.likebook.model.DTOs;

import com.example.likebook.model.entity.Mood;
import com.example.likebook.model.entity.User;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.List;

public class PostViewModel {
    private Long id;
    private String content;
    private User user;
    private List<User> likes;
    private Mood mood;

    public PostViewModel() {
    }

    public Long getId() {
        return id;
    }

    public PostViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostViewModel setContent(String content) {
        this.content = content;
        return this;
    }

    public User getUser() {
        return user;
    }

    public PostViewModel setUser(User user) {
        this.user = user;
        return this;
    }

    public List<User> getLikes() {
        return likes;
    }

    public PostViewModel setLikes(List<User> likes) {
        this.likes = likes;
        return this;
    }

    public Mood getMood() {
        return mood;
    }

    public PostViewModel setMood(Mood mood) {
        this.mood = mood;
        return this;
    }
}
