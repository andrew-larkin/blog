package com.skillbox.AndrewBlog.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_votes")
public class PostVote {

    public PostVote() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private int userId;

    @Column(name = "post_id", nullable = false, insertable = false, updatable = false)
    private int postId;

    @JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    @Column(name = "time", columnDefinition = "DATETIME NOT NULL")
    private Date time;

    @Column(name = "value", columnDefinition = "TINYINT NOT NULL")
    private byte value;

    public PostVote(int userId, int postId, Date time, byte value) {
        this.userId = userId;
        this.postId = postId;
        this.time = time;
        this.value = value;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "post_id")
    private Post post;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
