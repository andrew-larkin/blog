package com.skillbox.AndrewBlog.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillbox.AndrewBlog.model.ModerationStatus;

public class ModerationRequest {

    @JsonProperty("post_id")
    private int postId;
    private String decision;

    public ModerationRequest(int postId, String decision) {
        this.postId = postId;
        this.decision = decision;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
