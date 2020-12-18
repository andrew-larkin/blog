package com.Skillbox.AndrewBlog.service;

import com.Skillbox.AndrewBlog.api.response.CheckResponse;
import org.springframework.stereotype.Service;

@Service
public class CheckService {

    CheckResponse checkResponse = new CheckResponse();


    public CheckResponse getCheck() {
        checkResponse.setResult("false");
        return checkResponse;
    }

}
