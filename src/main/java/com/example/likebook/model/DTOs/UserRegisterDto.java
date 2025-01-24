package com.example.likebook.model.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UserRegisterDto {
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Password cannot be empty string")
    @Size(min = 3, max = 20, message = "Password must be between 3 and 20 characters")
    private String password;
    @NotBlank(message = "Password cannot be empty string")
    @Size(min = 3, max = 20, message = "Password must be between 3 and 20 characters")
    private String confirmPassword;
    @Email(message = "Email must be valid")
    private String email;
    @NotNull(message = "You must select at least one role")
    @Size(min = 1, message = "At least one role must be selected")
    private Set<String> userRoles;

    public UserRegisterDto() {
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public UserRegisterDto setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
