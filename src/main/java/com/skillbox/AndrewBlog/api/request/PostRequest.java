package com.skillbox.AndrewBlog.api.request;

import java.util.List;

public class PostRequest {

    long timestamp;
    int active;
    String title;
    List<String> tags;
    String text;

    public PostRequest(long timestamp, int active, String title, List<String> tags, String text) {
        this.timestamp = timestamp;
        this.active = active;
        this.title = title;
        this.tags = tags;
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
