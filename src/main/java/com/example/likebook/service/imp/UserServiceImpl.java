package com.example.likebook.service.imp;

import com.example.likebook.model.DTOs.UserRegisterDto;
import com.example.likebook.model.entity.User;
import com.example.likebook.model.entity.UserRole;
import com.example.likebook.model.entity.UserRoleEnum;
import com.example.likebook.repository.UserRepository;
import com.example.likebook.repository.UserRoleRepository;
import com.example.likebook.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder,
                           UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public void registerAndLogin(UserRegisterDto userRegisterDto) {
        Set<UserRole> roles=userRegisterDto.getUserRoles()
                .stream().map(role->userRoleRepository.findByUserRole(UserRoleEnum.valueOf(role))
                        .orElseThrow(()->new IllegalArgumentException("Role not found: "+role)))
                .collect(Collectors.toSet());

        User newUser = new User()
                .setUserRoles(roles)
                .setEmail(userRegisterDto.getEmail())
                .setUsername(userRegisterDto.getUsername())
                .setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userRepository.save(newUser);
        System.out.println("User saved: " + newUser.getUsername());
        UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getUsername());
        System.out.println("MyUserDetails loaded: " + userDetails.getUsername());
        if (userDetails == null) {
            throw new IllegalStateException("MyUserDetails could not be loaded after registration");
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public boolean checkIfUserExists(UserRegisterDto userRegisterDto) {
        Optional<User> user = userRepository.findByUsername(userRegisterDto.getUsername());
        return user.isPresent();
    }

    public void init() {

        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
            UserRole adminRole = new UserRole().setUserRole(UserRoleEnum.ADMIN);
            UserRole moderatorRole = new UserRole().setUserRole(UserRoleEnum.MODERATOR);
            adminRole = userRoleRepository.save(adminRole);
            moderatorRole = userRoleRepository.save(moderatorRole);
            initAdmin(Set.of(adminRole, moderatorRole));
            initModerator(Set.of(moderatorRole));

        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElse(null);
    }

    private void initAdmin(Set<UserRole> roles) {
        User admin = new User()
                .setUserRoles(roles)
                .setUsername("admina")
                .setEmail("admina@abv.bg")
                .setPassword(passwordEncoder.encode("topsecret"));
        userRepository.save(admin);
    }

    private void initModerator(Set<UserRole> roles) {
        User mode = new User()
                .setUserRoles(roles)
                .setUsername("moderator")
                .setEmail("mode@abv.bg")
                .setPassword(passwordEncoder.encode("12345"));
        userRepository.save(mode);

    }
}
