package com.skillbox.AndrewBlog.controller;

import com.skillbox.AndrewBlog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {

    private final PostService postService;

    @Autowired
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/api/post")
    public ResponseEntity<?> getApiPosts(@RequestParam int offset,
                                         @RequestParam int limit,
                                         @RequestParam String mode) {
        return postService.getApiPosts(offset, limit, mode);
    }
}
