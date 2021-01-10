package com.skillbox.AndrewBlog.api.response;

import java.util.List;

public class TagListResponse {

    private List<String> tags;

    public TagListResponse(List<String> tags) {
        this.tags = tags;
    }
}
