package com.skillbox.AndrewBlog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skillbox.AndrewBlog.model.Post;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_comments")
public class PostComment {

    public PostComment() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "parent_id")
    private int parentId;

    @Column(name = "post_id", nullable = false, insertable = false, updatable = false)
    private int postId;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private int userId;

    @JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    @Column(name = "time", columnDefinition = "DATETIME NOT NULL")
    private Date time;

    @Column(name = "text", columnDefinition = "TEXT NOT NULL")
    private String text;

    public PostComment(int parentId, int postId, int userId, Date time, String text) {
        this.parentId = parentId;
        this.postId = postId;
        this.userId = userId;
        this.time = time;
        this.text = text;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "post_id")
    private Post post;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
