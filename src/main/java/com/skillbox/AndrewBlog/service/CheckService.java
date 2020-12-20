package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.CheckResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckService {

    private CheckResponse checkResponse = new CheckResponse();

    @Autowired
    public CheckService(CheckResponse checkResponse) {
        this.checkResponse = checkResponse;
    }

    public ResponseEntity<?> getApiAuthCheck() {
        checkResponse.setResult("false");
        return ResponseEntity.status(HttpStatus.OK).body(checkResponse);
    }

}
