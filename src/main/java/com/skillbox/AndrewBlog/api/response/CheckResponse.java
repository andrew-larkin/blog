package com.skillbox.AndrewBlog.api.response;

public class CheckResponse {

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;

    public CheckResponse(String result) {
        this.result = result;
    }

    public CheckResponse() {
    }
}
