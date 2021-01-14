package com.skillbox.AndrewBlog.api.response;

public class NameWeightResponse {

    private String name;
    private String weight;

    public NameWeightResponse(String name, String weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
