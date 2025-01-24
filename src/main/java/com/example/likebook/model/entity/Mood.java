package com.example.likebook.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "moods")
public class Mood extends BaseEntity{
    @Enumerated(value = EnumType.STRING)
    private MoodName name;
    @Column(columnDefinition = "TEXT")
    private String description;

    public Mood() {
    }

    public MoodName getName() {
        return name;
    }

    public Mood setName(MoodName name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Mood setDescription(String description) {
        this.description = description;
        return this;
    }
}
