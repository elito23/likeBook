package com.example.likebook.web;

import com.example.likebook.model.DTOs.UserRegisterDto;
import com.example.likebook.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterDto")) {
            model.addAttribute("userRegisterDto", new UserRegisterDto());
            model.addAttribute("exists", false);
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDto userRegisterDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto", bindingResult);
            System.out.println("Validation failed or passwords do not match");
            return "redirect:/users/register";
        }
        boolean exists = userService.checkIfUserExists(userRegisterDto);
        if (exists) {
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes.addFlashAttribute("exists", true);
            System.out.println("user exists error");
            return "redirect:/users/register";
        }
        userService.registerAndLogin(userRegisterDto);
        System.out.println("everything is successfull");
        return "redirect:/";

    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, required = false) String username,
                        @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("bad_credentials", true);
        }
        model.addAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("username", username);
        redirectAttributes.addFlashAttribute("bad_credentials", true);

        return "redirect:/users/login?error=true";
    }

    @GetMapping("/logout")
    public String logout() {

        return "redirect:/";
    }
}
