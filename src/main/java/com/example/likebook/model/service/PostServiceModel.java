package com.example.likebook.model.service;

import com.example.likebook.model.entity.Mood;
import com.example.likebook.model.entity.MoodName;
import com.example.likebook.model.entity.User;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.List;

public class PostServiceModel {
    private String content;
    private User user;
    private List<User> likes;
    private MoodName mood;

    public PostServiceModel() {
    }

    public String getContent() {
        return content;
    }

    public PostServiceModel setContent(String content) {
        this.content = content;
        return this;
    }

    public User getUser() {
        return user;
    }

    public PostServiceModel setUser(User user) {
        this.user = user;
        return this;
    }

    public List<User> getLikes() {
        return likes;
    }

    public PostServiceModel setLikes(List<User> likes) {
        this.likes = likes;
        return this;
    }

    public MoodName getMood() {
        return mood;
    }

    public PostServiceModel setMood(MoodName mood) {
        this.mood = mood;
        return this;
    }
}
