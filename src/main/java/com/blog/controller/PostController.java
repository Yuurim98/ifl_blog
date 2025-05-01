package com.blog.controller;

import com.blog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostController {

    @GetMapping("/posts")
    public String get() {
        return "hello";
    }

    @PostMapping("/posts")
    public String post(@RequestBody PostCreate postCreate) {
        log.info("postCreate={}", postCreate.toString());
        return "hello";
    }
}
