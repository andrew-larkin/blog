package com.skillbox.AndrewBlog.model;

import com.skillbox.AndrewBlog.security.ApplicationUserRole;
import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private ApplicationUserRole applicationUserRole;

    Role(ApplicationUserRole applicationUserRole) {
        this.applicationUserRole = applicationUserRole;
    }

    public Role() {
    }

    @Override
    public String getAuthority() {
        return applicationUserRole.getRole();
    }

    public ApplicationUserRole getApplicationUserRole() {
        return applicationUserRole;
    }

    public void setApplicationUserRole(ApplicationUserRole applicationUserRole) {
        this.applicationUserRole = applicationUserRole;
    }
}
