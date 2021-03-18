package com.skillbox.AndrewBlog.controller;

import com.skillbox.AndrewBlog.api.request.EmailRequest;
import com.skillbox.AndrewBlog.api.request.LoginRequest;
import com.skillbox.AndrewBlog.api.request.PasswordRequest;
import com.skillbox.AndrewBlog.api.request.RegisterRequest;
import com.skillbox.AndrewBlog.security.PersonDetailsService;
import com.skillbox.AndrewBlog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthService authService;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public ApiAuthController(AuthService authService, PersonDetailsService personDetailsService) {
        this.authService = authService;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/check")
    public ResponseEntity<?> getApiAuthCheck() {
        return authService.getApiAuthCheck();
    }

    @GetMapping("/captcha")
    private ResponseEntity<?> getApiAuthCaptcha() {
        return authService.getApiAuthCaptcha();
    }

    @PostMapping("/register")
    private ResponseEntity<?> postApiAuthRegister(@RequestBody RegisterRequest registerRequest) {
        return authService.postApiAuthRegister(registerRequest);
    }

    @PostMapping("/login")
    private ResponseEntity<?> postApiAuthLogin(@RequestBody LoginRequest loginRequest) {
        return personDetailsService.postApiAuthLogin(loginRequest);
    }

    @PostMapping("/restore")
    private ResponseEntity<?> postApiAuthRestore(@RequestBody EmailRequest emailRequest) {
        return authService.postApiAuthRestore(emailRequest);
    }

    @PostMapping("/password")
    private ResponseEntity<?> postApiAuthPassword(@RequestBody PasswordRequest passwordRequest) {
        return authService.postApiAuthPassword(passwordRequest);
    }

    //@Secured({"USER", "MODERATOR"})
    @GetMapping("/logout")
    private ResponseEntity<?> getApiAuthLogout() {
        return personDetailsService.getApiAuthLogout();
    }
}