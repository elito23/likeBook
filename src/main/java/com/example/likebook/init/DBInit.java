package com.example.likebook.init;

import com.example.likebook.service.MoodService;
import com.example.likebook.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final MoodService moodService;
    private final UserService userService;

    public DBInit(MoodService moodService, UserService userService) {
        this.moodService = moodService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        moodService.initMoods();
        userService.init();

    }
}
