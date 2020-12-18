package com.Skillbox.AndrewBlog.api.request;

public class PostRequest {

    private int offset;
    private int limit;
    private String mode;

    public PostRequest(int offset, int limit, String mode) {
        this.offset = offset;
        this.limit = limit;
        this.mode = mode;
    }

    public PostRequest() {
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
