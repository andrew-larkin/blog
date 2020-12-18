package com.Skillbox.AndrewBlog.api.response;

import java.util.List;

public class PostResponse {

    public PostResponse() {
    }

    private int count;
    private List<PostTempForResponse> posts;

    public List<PostTempForResponse> getPosts() {
        return posts;
    }

    public void setPosts(List<PostTempForResponse> posts) {
        this.posts = posts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
