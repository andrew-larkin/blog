package com.skillbox.AndrewBlog.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.skillbox.AndrewBlog.security.ApplicationUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name="users")
public class User implements UserDetails {

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "is_moderator", columnDefinition = "TINYINT NOT NULL")
    private byte isModerator;

    @JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    @Column(name = "reg_time", columnDefinition = "DATETIME NOT NULL")
    private Date regTime;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "code", length = 255)
    private String code;

    @Column(name = "photo", columnDefinition = "TEXT")
    private String photo;

    public User(byte isModerator, Date regTime, String name, String email, String password, String code, String photo) {
        this.isModerator = isModerator;
        this.regTime = regTime;
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.photo = photo;
    }

    public User(int id, byte isModerator, Date regTime, String name, String email) {
        this.id = id;
        this.isModerator = isModerator;
        this.regTime = regTime;
        this.name = name;
        this.email = email;
    }

    public User(String email, String name, String password, String captcha) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.isModerator = 0;
        this.code = captcha;

        setRegTime(Instant.now().toEpochMilli());
        setRoles();
    }


    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<PostComment> postComments;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<PostVote> postVotes;

    @Transient
    private Set<Role> roles = new HashSet<>();

    public long getTimestamp() {
        return regTime.getTime() / 1000;
    }

    public void setRegTime(long timestamp) {
        regTime = Date.from(Instant.ofEpochMilli(timestamp));
    }

    public void setRoles() {
        roles.add(new Role(ApplicationUserRole.USER));
        if (isModerator == 1) {
            roles.add(new Role(ApplicationUserRole.MODERATOR));
        }
    }

    public Set<Role> getRoles() {
        if (roles.size() == 0) {
            setRoles();
        }
        return roles;
    }

    public void setRoles(Set<Role> set) {
        setRoles();
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getIsModerator() {
        return isModerator;
    }

    public void setIsModerator(byte isModerator) {
        this.isModerator = isModerator;
    }

    public int getId() {return id;}

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}
