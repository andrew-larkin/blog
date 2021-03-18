package com.skillbox.AndrewBlog.controller;

import com.skillbox.AndrewBlog.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    //@Secured("USER")
    @PostMapping("/image")
    public ResponseEntity<?> uploadFile(@RequestParam("type") String type,
                                        @RequestParam(name = "file", required = false) MultipartFile file) {
        return imageService.getUpload(type, file);
    }
}

