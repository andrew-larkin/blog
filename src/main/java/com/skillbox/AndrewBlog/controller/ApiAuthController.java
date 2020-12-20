package com.skillbox.AndrewBlog.controller;

import com.skillbox.AndrewBlog.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final CheckService checkService;

    @Autowired
    public ApiAuthController(CheckService checkService) {
        this.checkService = checkService;
    }

    @GetMapping("/check")
    public ResponseEntity<?> getApiAuthCheck() {
        return checkService.getApiAuthCheck();
    }
}
