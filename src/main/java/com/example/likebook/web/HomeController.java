package com.example.likebook.web;

import com.example.likebook.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private  final PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String index(Model model,
                        @AuthenticationPrincipal UserDetails userDetails){
        if(userDetails==null){
            return "index";
        }
        model.addAttribute("myPosts",postService.findAllPostsByCurrentUser(userDetails.getUsername()));
        model.addAttribute("allOtherPosts",postService.findAllPosts(userDetails.getUsername()));
        model.addAttribute("postsCount",postService.findOtherPostsCount(userDetails.getUsername()));
        return "home";
    }
}
