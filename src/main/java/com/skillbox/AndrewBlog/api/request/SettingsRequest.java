package com.skillbox.AndrewBlog.api.request;

public class SettingsRequest {

    private boolean MULTIUSER_MODE;
    private boolean POST_PREMODERATION;
    private boolean STATISTICS_IS_PUBLIC;


    public SettingsRequest(boolean multiuser_mode, boolean post_premoderation, boolean statistics_is_public) {
        MULTIUSER_MODE = multiuser_mode;
        POST_PREMODERATION = post_premoderation;
        STATISTICS_IS_PUBLIC = statistics_is_public;
    }

    public boolean isMULTIUSER_MODE() {
        return MULTIUSER_MODE;
    }

    public boolean isPOST_PREMODERATION() {
        return POST_PREMODERATION;
    }

    public boolean isSTATISTICS_IS_PUBLIC() {
        return STATISTICS_IS_PUBLIC;
    }
}
