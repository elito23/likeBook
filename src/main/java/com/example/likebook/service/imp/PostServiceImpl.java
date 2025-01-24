package com.example.likebook.service.imp;

import com.example.likebook.model.DTOs.PostViewModel;
import com.example.likebook.model.entity.Post;
import com.example.likebook.model.entity.User;
import com.example.likebook.model.service.PostServiceModel;
import com.example.likebook.repository.MoodRepository;
import com.example.likebook.repository.PostRepository;
import com.example.likebook.repository.UserRepository;
import com.example.likebook.service.PostService;
import com.example.likebook.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final ModelMapper modelMapper;
    private final MoodRepository moodRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public PostServiceImpl(ModelMapper modelMapper, MoodRepository moodRepository, PostRepository postRepository, UserRepository userRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.moodRepository = moodRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void add(PostServiceModel postServiceModel, String username) {
        System.out.println();
        Post post = modelMapper.map(postServiceModel, Post.class);
        post.setMood(moodRepository.findByName(postServiceModel.getMood()));
        post.setUser(userService.findByUsername(username));
        postRepository.save(post);
        System.out.println();
    }

    @Override
    public List<PostViewModel> findAllPosts(String username) {
        return postRepository.findAllByUser_UsernameNot(username)
                .stream().map(post -> modelMapper.map(post, PostViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostViewModel> findAllPostsByCurrentUser(String username) {
        return postRepository.findAllByUser_Username(username)
                .stream().map(post -> modelMapper.map(post, PostViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Long findOtherPostsCount(String username) {
        return postRepository.findOtherPostsCount(username);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void likeById(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        ("Post with id " + id + " not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException
                        ("User with username " + username + " not found"));
        if (!post.getLikes().contains(user)) {
            post.getLikes().add(user);
        }else{
            post.getLikes().remove(user);
        }
        postRepository.save(post);
    }


}
