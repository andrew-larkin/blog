package com.Skillbox.AndrewBlog.service;

import com.Skillbox.AndrewBlog.api.response.SettingsResponse;
import com.Skillbox.AndrewBlog.model.GlobalSettings;
import com.Skillbox.AndrewBlog.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsService implements SettingServiceInt {

    private SettingsRepository settingsRepository;

    @Autowired
    public SettingsService (SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public SettingsResponse getGlobalSettings () {

        SettingsResponse settingsResponse = new SettingsResponse();
        Iterable<GlobalSettings> settings = settingsRepository.findAll();
        List<GlobalSettings> list = new ArrayList<>();
        settings.forEach(list::add);
        settingsResponse.setMultiuserMode(list.get(0).getValue());
        settingsResponse.setPostPremoderation(list.get(1).getValue());
        settingsResponse.setStatisticIsPublic(list.get(2).getValue());

        return settingsResponse;
    }
}
