package com.skillbox.AndrewBlog.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDescriptionResponse {

    @JsonProperty("error_description")
    private String errorDescription;

    public ErrorDescriptionResponse(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
