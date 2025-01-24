package com.example.likebook.model.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUserDetails extends User {
    private final Long id;
    public MyUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id=id;
    }

    public Long getId() {
        return id;
    }
}
