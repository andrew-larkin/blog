package com.skillbox.AndrewBlog.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_votes")
public class PostVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

   /* @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private int userId;

    @Column(name = "post_id", nullable = false, insertable = false, updatable = false)
    private int postId;*/

    @JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    @Column(name = "time", columnDefinition = "DATETIME NOT NULL")
    private Date time;

    @Column(name = "value", columnDefinition = "TINYINT NOT NULL")
    private byte value;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "post_id")
    private Post post;

    public PostVote(Date time, byte value, User user, Post post) {
        this.time = time;
        this.value = value;
        this.user = user;
        this.post = post;
    }

    public PostVote() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}