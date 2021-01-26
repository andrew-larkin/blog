package com.skillbox.AndrewBlog.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CountPostsResponse {

    private int count;
    private Object posts;

    public CountPostsResponse(int count, Object posts) {
        this.count = count;
        this.posts = posts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getPosts() {
        return posts;
    }

    public void setPosts(Object posts) {
        this.posts = posts;
    }
}
