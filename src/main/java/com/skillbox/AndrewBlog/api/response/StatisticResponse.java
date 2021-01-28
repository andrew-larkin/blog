package com.skillbox.AndrewBlog.api.response;

public class StatisticResponse {

    private int postCount;
    private int likesCount;
    private int dislikeCount;
    private int viewCount;
    private long firstPublication;

    public StatisticResponse(int postCount, int likesCount, int dislikeCount, int viewCount, long firstPublication) {
        this.postCount = postCount;
        this.likesCount = likesCount;
        this.dislikeCount = dislikeCount;
        this.viewCount = viewCount;
        this.firstPublication = firstPublication;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public long getFirstPublication() {
        return firstPublication;
    }

    public void setFirstPublication(long firstPublication) {
        this.firstPublication = firstPublication;
    }
}
