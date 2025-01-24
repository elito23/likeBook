package com.example.likebook.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user_roles")
public class UserRole extends BaseEntity{
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    public UserRole() {
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public UserRole setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }
}
