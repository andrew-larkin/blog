package com.skillbox.AndrewBlog.api.request;

public class ProfileRequest {

    private String name;
    private String email;
    private String password;
    private byte removePhoto;
    private String photo;

    public ProfileRequest(String name, String email, String password, byte removePassword, String photo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.removePhoto = removePassword;
        this.photo = photo;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getRemovePhoto() {
        return removePhoto;
    }

    public void setRemovePhoto(byte removePhoto) {
        this.removePhoto = removePhoto;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
