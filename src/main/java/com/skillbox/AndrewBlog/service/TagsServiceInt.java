package com.Skillbox.AndrewBlog.service;

import com.Skillbox.AndrewBlog.api.response.Tags;
import com.Skillbox.AndrewBlog.api.response.TagsResponse;

import java.util.List;

public interface TagsServiceInt {

    TagsResponse getTags();
    TagsResponse getTags(String request);

}
