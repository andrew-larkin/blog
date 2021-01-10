package com.skillbox.AndrewBlog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillbox.AndrewBlog.api.response.CheckResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class CheckServiceTest {

    @Test
    void getApiAuthCheck() {

        CheckService checkService = new CheckService();
        ResponseEntity<?> responseEntity = checkService.getApiAuthCheck();
        CheckResponse checkResponse = new CheckResponse();
        checkResponse.setResult("false");
        Object h = responseEntity.getBody();

        assertEquals(checkResponse, responseEntity.getBody());

    }
}