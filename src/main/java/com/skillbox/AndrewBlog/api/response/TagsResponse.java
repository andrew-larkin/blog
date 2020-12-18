package com.Skillbox.AndrewBlog.api.response;

import java.util.ArrayList;
import java.util.List;

public class TagsResponse {

    List<Tags> tags = new ArrayList<>();

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }
}
