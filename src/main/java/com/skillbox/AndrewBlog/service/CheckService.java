package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.CheckResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckService {

    public ResponseEntity<?> getApiAuthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body(new CheckResponse("false"));
    }

}
