package com.blog.controller;

import com.blog.request.PostCreate;
import jakarta.validation.Valid;
import java.util.Map;
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
    public Map<String, String> post(@RequestBody @Valid PostCreate postCreate) {

        return Map.of();
    }
}
