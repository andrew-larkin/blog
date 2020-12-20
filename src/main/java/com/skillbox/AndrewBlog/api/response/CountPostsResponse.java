package com.skillbox.AndrewBlog.api.response;

public class CountPostsResponse {

    private int count;
    private Object posts;

    public CountPostsResponse(int count, Object posts) {
        this.count = count;
        this.posts = posts;
    }

    public CountPostsResponse() {
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

}
