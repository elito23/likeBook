package com.example.likebook.model.DTOs;

import com.example.likebook.model.entity.MoodName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostAddDto {
    @Size(min = 2, max = 150, message = "Content must be between 2 and 150 characters")
    @NotBlank(message = "Content cannot be empty")
    private String content;
    @NotNull(message = "You must select the mood")
    private MoodName mood;

    public PostAddDto() {
    }

    public String getContent() {
        return content;
    }

    public PostAddDto setContent(String content) {
        this.content = content;
        return this;
    }

    public MoodName getMood() {
        return mood;
    }

    public PostAddDto setMood(MoodName mood) {
        this.mood = mood;
        return this;
    }
}
