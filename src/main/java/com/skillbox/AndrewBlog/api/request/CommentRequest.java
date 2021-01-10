package com.skillbox.AndrewBlog.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentRequest {

    @JsonProperty("parent_id")
    int parentId;
    @JsonProperty("post_id")
    int postId;
    String text;

    public CommentRequest(int parentId, int postId, String text) {
        this.parentId = parentId;
        this.postId = postId;
        this.text = text;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
