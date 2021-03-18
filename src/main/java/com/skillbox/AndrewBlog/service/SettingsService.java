package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.request.SettingsRequest;
import com.skillbox.AndrewBlog.api.response.SettingsResponse;
import com.skillbox.AndrewBlog.model.GlobalSettings;
import com.skillbox.AndrewBlog.repository.SettingsRepository;
import org.apache.tomcat.jni.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    private SettingsRepository settingsRepository;

    @Autowired
    public SettingsService (SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public ResponseEntity<?> getApiSettings () {

        return ResponseEntity.status(HttpStatus.OK).body(new SettingsResponse(
                settingsRepository.findById(1).get().getValue().equals("YES"),
                settingsRepository.findById(2).get().getValue().equals("YES"),
                settingsRepository.findById(3).get().getValue().equals("YES")
        ));
    }

    public ResponseEntity<?> putApiSettings(SettingsRequest settingsRequest) {
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
