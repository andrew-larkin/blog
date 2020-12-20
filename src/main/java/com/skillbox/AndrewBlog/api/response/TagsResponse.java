package com.skillbox.AndrewBlog.api.response;

public class TagsResponse {

   private Object tags = new Object();

    public TagsResponse(Object tags) {
        this.tags = tags;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }
}
