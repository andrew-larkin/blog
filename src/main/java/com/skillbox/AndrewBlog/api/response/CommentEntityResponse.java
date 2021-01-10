package com.skillbox.AndrewBlog.api.response;

public class CommentEntityResponse {

    private long id;
    private long timestamp;
    private String text;
    private IdNamePhotoResponse user;

    public CommentEntityResponse(long id, long timestamp, String text, IdNamePhotoResponse user) {
        this.id = id;
        this.timestamp = timestamp;
        this.text = text;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public IdNamePhotoResponse getUser() {
        return user;
    }

    public void setUser(IdNamePhotoResponse user) {
        this.user = user;
    }
}
