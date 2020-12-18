package com.Skillbox.AndrewBlog.service;

import com.Skillbox.AndrewBlog.api.request.PostRequest;
import com.Skillbox.AndrewBlog.api.response.PostResponse;

public interface PostServiceInt {

    PostResponse getPosts(PostRequest postRequest);
}
