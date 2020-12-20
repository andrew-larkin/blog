package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.SettingsResponse;
import com.skillbox.AndrewBlog.repository.SettingsRepository;
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
                settingsRepository.getGlobalSettingsByName("MULTIUSER_MODE"),
                settingsRepository.getGlobalSettingsByName("POST_PREMODERATION"),
                settingsRepository.getGlobalSettingsByName("STATISTICS_IS_PUBLIC")
        ));
    }
}
