package com.skillbox.AndrewBlog.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LikeRequest {

    @JsonProperty("post_id")
    private int postId;

    public LikeRequest() {
    }

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
