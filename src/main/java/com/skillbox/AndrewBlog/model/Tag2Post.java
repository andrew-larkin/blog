package com.skillbox.AndrewBlog.model;

import javax.persistence.*;

@Entity
@Table(name = "tag2post")
public class Tag2Post {

    public Tag2Post() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "post_id", nullable = false, insertable = false, updatable = false)
    private int postId;

    @Column(name = "tag_id", nullable = false, insertable = false, updatable = false)
    private int tagId;

    public Tag2Post(int postId, int tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
