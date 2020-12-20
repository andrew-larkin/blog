package com.skillbox.AndrewBlog.model;

import javax.persistence.*;

@Entity
@Table(name = "global_settings")
public class GlobalSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    int id;

    @Column(name = "code", nullable = false, length = 255)
    String code;

    @Column(name = "name", nullable = false, length = 255)
    String name;

    @Column(name = "value", nullable = false, length = 255)
    String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
