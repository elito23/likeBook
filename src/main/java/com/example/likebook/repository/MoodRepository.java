package com.example.likebook.repository;

import com.example.likebook.model.entity.Mood;
import com.example.likebook.model.entity.MoodName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodRepository extends JpaRepository<Mood,Long> {
Mood findByName(MoodName moodName);
}
