package com.skillbox.AndrewBlog.api.response;

import java.util.*;

public class YearsPostsResponse {

    private List<Integer> years;
    private Map<String, Integer> posts;

    public YearsPostsResponse(List<Integer> years, Map<String, Integer> posts) {
        this.years = years;
        this.posts = posts;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public Map<String, Integer> getPosts() {
        return posts;
    }

    public void setPosts(Map<String, Integer> posts) {
        this.posts = posts;
    }
}
