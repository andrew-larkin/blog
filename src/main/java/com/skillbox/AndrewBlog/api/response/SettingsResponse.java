package com.Skillbox.AndrewBlog.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingsResponse {

    @JsonProperty("MULTIUSER_MODE")
    private String multiuserMode;

    @JsonProperty("POST_PREMODERATION")
    private String postPremoderation;

    @JsonProperty("STATISTIC_IS_PUBLIC")
    private String statisticIsPublic;

    public String getMultiuserMode() {
        return multiuserMode;
    }

    public void setMultiuserMode(String multiuserMode) {
        this.multiuserMode = multiuserMode;
    }

    public String getPostPremoderation() {
        return postPremoderation;
    }

    public void setPostPremoderation(String postPremoderation) {
        this.postPremoderation = postPremoderation;
    }

    public String getStatisticIsPublic() {
        return statisticIsPublic;
    }

    public void setStatisticIsPublic(String statisticIsPublic) {
        this.statisticIsPublic = statisticIsPublic;
    }
}
