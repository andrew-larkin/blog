package com.skillbox.AndrewBlog.api.request;

public class LikeRequest {

    private int postId;

    public LikeRequest(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
