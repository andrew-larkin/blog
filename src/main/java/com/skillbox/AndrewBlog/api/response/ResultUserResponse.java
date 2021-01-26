package com.skillbox.AndrewBlog.api.response;

public class ResultUserResponse {

    private boolean result;
    private Object user;

    public ResultUserResponse(boolean result, Object user) {
        this.result = result;
        this.user = user;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}
