package com.skillbox.AndrewBlog.api.response;

import java.util.Map;

public class ResultErrorsResponse {

    private boolean result;
    private Map<String, String> errors;

    public ResultErrorsResponse(boolean result, Map<String, String> errors) {
        this.result = result;
        this.errors = errors;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}