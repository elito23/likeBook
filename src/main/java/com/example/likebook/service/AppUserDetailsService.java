package com.example.likebook.service;

import com.example.likebook.model.entity.MyUserDetails;
import com.example.likebook.model.entity.User;
import com.example.likebook.model.entity.UserRole;
import com.example.likebook.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//this is not annotated as service because we return it as a BEAN
public class AppUserDetailsService implements UserDetailsService {
    public UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user with username: " + username);
        return userRepository
                .findByUsername(username)
                .map(this::map)
                .orElseThrow(()->new UsernameNotFoundException("User with username "+username+" not found"));

    }
    private UserDetails map(User user){
//        return org.springframework.security.core.userdetails.User
//                        .builder()
//                        .username(user.getUsername())
//                        .password(user.getPassword())
//                        .authorities(user.getUserRoles()
//                                .stream()
//                                .map(this::map)
//                                .toList())
//                .build();
        return new MyUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getUserRoles()
                        .stream()
                        .map(this::map)
                        .toList());
    }
    private GrantedAuthority map(UserRole userRole){
        return new SimpleGrantedAuthority(userRole.getUserRole().name());
    }
}
