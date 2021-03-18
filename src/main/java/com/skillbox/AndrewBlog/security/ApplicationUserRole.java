package com.skillbox.AndrewBlog.security;

public enum ApplicationUserRole {
    USER("ROLE_USER"),
    MODERATOR("ROLE_MODERATOR");

    private final String role;

    ApplicationUserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
