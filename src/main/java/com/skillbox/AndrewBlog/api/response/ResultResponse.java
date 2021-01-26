package com.skillbox.AndrewBlog.api.response;

public class ResultResponse {

    private boolean result;

    public ResultResponse(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
