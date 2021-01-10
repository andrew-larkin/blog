package com.skillbox.AndrewBlog.api.response;

import java.util.List;

public class CountPostsResponse {

    private int count;
    private List<PostEntityResponse> posts;

    public CountPostsResponse(int count, List<PostEntityResponse> posts) {
        this.count = count;
        this.posts = posts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PostEntityResponse> getPostEntityResponseList() {
        return posts;
    }

    public void setPostEntityResponseList(List<PostEntityResponse> posts) {
        this.posts = posts;
    }
}
