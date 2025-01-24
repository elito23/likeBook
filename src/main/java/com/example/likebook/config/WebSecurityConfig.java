package com.example.likebook.config;

import com.example.likebook.model.entity.UserRoleEnum;
import com.example.likebook.repository.UserRepository;
import com.example.likebook.service.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf // CSRF configuration
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // Example: Storing CSRF tokens in cookies
                )
                .authorizeHttpRequests((requests) -> requests
                        //with this line we allow access to all static resources(css,images,js) but they must be with the default names
                        //.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        //this line allows access to the home, login and registration page to anyone
                        .requestMatchers("/", "/css/**", "/images/**", "/users/login", "/users/register").permitAll()
                        .requestMatchers("/posts/add").hasAuthority(UserRoleEnum.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                                .loginPage("/users/login")
                                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                //the name of the <input...> HTML field that keeps the password
                                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                                //the page that we will be redirected to when successfully logged in
                                .defaultSuccessUrl("/")
                                .failureForwardUrl("/users/login-error")
                        //.permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
        ;
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }
}
