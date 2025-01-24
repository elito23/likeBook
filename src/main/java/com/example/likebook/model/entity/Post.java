package com.example.likebook.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Column(nullable = false)
    private String content;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<User> likes;
    @ManyToOne
    private Mood mood;
    public Post() {
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Post setUser(User user) {
        this.user = user;
        return this;
    }

    public List<User> getLikes() {
        return likes;
    }

    public Post setLikes(List<User> likes) {
        this.likes = likes;
        return this;
    }

    public Mood getMood() {
        return mood;
    }

    public Post setMood(Mood mood) {
        this.mood = mood;
        return this;
    }
}
