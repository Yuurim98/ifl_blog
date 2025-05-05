package com.blog.controller;

import com.blog.domain.Post;
import com.blog.request.PostCreate;
import com.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate postCreate) {
        postService.write(postCreate);
    }

    @GetMapping("/posts/{postId}")
    public Post getPost(@PathVariable Long postId) {
        Post post = postService.get(postId);
        return post;
    }
}
