package com.example.likebook.service.imp;

import com.example.likebook.model.entity.Mood;
import com.example.likebook.model.entity.MoodName;
import com.example.likebook.repository.MoodRepository;
import com.example.likebook.service.MoodService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MoodServiceImpl implements MoodService {
   private final MoodRepository moodRepository;

    public MoodServiceImpl(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Override
    public void initMoods() {
        if(moodRepository.count()==0){
            Arrays.stream(MoodName.values())
                    .forEach(name->{
                        Mood mood=new Mood();
                        switch(name.name()){
                            case "HAPPY":
                                mood.setDescription("This is the best mood!");
                                break;
                            case "SAD":
                                mood.setDescription("This is not the best mood!");
                                break;
                            case "INSPIRED":
                                mood.setDescription("This is good mood too!");
                                break;
                        }
                        mood.setName(name);
                        moodRepository.save(mood);
                    });
        }
    }


}
