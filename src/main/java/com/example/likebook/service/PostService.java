package com.example.likebook.service;

import com.example.likebook.model.DTOs.PostViewModel;
import com.example.likebook.model.service.PostServiceModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PostService {
    void add(PostServiceModel postServiceModel, String username);

    List<PostViewModel> findAllPosts(String username);

    void deleteById(Long id);

    List<PostViewModel> findAllPostsByCurrentUser(String username);

    Long findOtherPostsCount(String username);

    void likeById(Long id,String username);
}
