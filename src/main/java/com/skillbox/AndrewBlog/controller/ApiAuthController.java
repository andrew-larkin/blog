package com.Skillbox.AndrewBlog.controller;

import com.Skillbox.AndrewBlog.api.response.CheckResponse;
import com.Skillbox.AndrewBlog.service.CheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class ApiAuthController {

    CheckService checkService = new CheckService();

    public ApiAuthController(CheckService checkService) {
        this.checkService = checkService;
    }

    @GetMapping("/check")
    public ResponseEntity<CheckResponse> apiAuth() {
        final CheckResponse checkResponse = checkService.getCheck();
        return new ResponseEntity<>(checkResponse, HttpStatus.OK);
    }
}
