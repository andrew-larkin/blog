package com.skillbox.AndrewBlog.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {

    public ResponseEntity<?> postApiProfileMy(MultipartFile photo, String email, String name,
                                              String password, byte removePhoto) {

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

}
