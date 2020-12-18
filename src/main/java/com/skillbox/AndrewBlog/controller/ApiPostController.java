package com.Skillbox.AndrewBlog.controller;

import com.Skillbox.AndrewBlog.api.request.PostRequest;
import com.Skillbox.AndrewBlog.api.response.PostResponse;
import com.Skillbox.AndrewBlog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {

    private final PostService postService;

    public ApiPostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/api/post")
    private ResponseEntity<PostResponse> getPosts(@RequestBody PostRequest postRequest) {
        final PostResponse postResponse = postService.getPosts(postRequest);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
}
