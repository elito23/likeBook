package com.example.likebook.web;

import com.example.likebook.model.DTOs.PostAddDto;
import com.example.likebook.model.entity.MyUserDetails;
import com.example.likebook.model.service.PostServiceModel;
import com.example.likebook.service.PostService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/posts")
public class PostController {
    private final ModelMapper modelMapper;
    private final PostService postService;

    public PostController(ModelMapper modelMapper, PostService postService) {
        this.modelMapper = modelMapper;
        this.postService = postService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        if (!model.containsAttribute("postAddDto")) {
            model.addAttribute("postAddDto", new PostAddDto());
        }
        return "post-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid PostAddDto postAddDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes
//                             ,@AuthenticationPrincipal MyUserDetails myUserDetails
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof MyUserDetails) {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            System.out.println("Authenticated user ID: " + userDetails.getId());

            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("postAddDto", postAddDto);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.postAddDto", bindingResult);
                return "redirect:add";
            }
            System.out.println("No validation errors. Proceeding to add post.");
            postService.add(modelMapper.map(postAddDto, PostServiceModel.class), userDetails.getUsername());
            System.out.println("Post added successfully.");
            return "redirect:/";
        } else {
            System.out.println("User is not authenticated or principal is not of type MyUserDetails.");
            return "redirect:users/login";
        }

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        postService.deleteById(id);
        return "redirect:/";
    }
    @GetMapping("/like/{id}")
    public String like(@PathVariable Long id,
                       @AuthenticationPrincipal UserDetails userDetails) {
        postService.likeById(id,userDetails.getUsername());
        return "redirect:/";
    }
}
