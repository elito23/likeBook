package com.example.likebook.service;

import com.example.likebook.model.DTOs.UserRegisterDto;
import com.example.likebook.model.entity.User;

public interface UserService {
    public void init();
    public User findByUsername(String username);
    public void registerAndLogin(UserRegisterDto userRegisterDto);
    boolean checkIfUserExists(UserRegisterDto userRegisterDto);
}
