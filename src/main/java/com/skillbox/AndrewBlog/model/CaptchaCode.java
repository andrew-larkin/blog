package com.skillbox.AndrewBlog.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "captcha_codes")
public class CaptchaCode {

    public CaptchaCode() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    int id;

    @JsonFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    @Column(name = "time", columnDefinition = "DATETIME NOT NULL")
    Date time;

    @Column(name = "code", columnDefinition = "TINYTEXT NOT NULL")
    String code;

    @Column(name = "secret_code", columnDefinition = "TINYTEXT NOT NULL")
    String secretCode;
}
