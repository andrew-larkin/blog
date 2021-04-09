package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.request.SettingsRequest;
import com.skillbox.AndrewBlog.api.response.SettingsResponse;
import com.skillbox.AndrewBlog.model.GlobalSettings;
import com.skillbox.AndrewBlog.model.User;
import com.skillbox.AndrewBlog.repository.SettingsRepository;
import com.skillbox.AndrewBlog.repository.UserRepository;
import com.skillbox.AndrewBlog.security.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsService {

    private final String YES = "YES";
    private final String NO = "NO";

    private final SettingsRepository settingsRepository;
    private final UserRepository userRepository;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SettingsService(SettingsRepository settingsRepository, UserRepository userRepository, PersonDetailsService personDetailsService) {
        this.settingsRepository = settingsRepository;
        this.userRepository = userRepository;
        this.personDetailsService = personDetailsService;
    }

    public ResponseEntity<?> getApiSettings () {

        return ResponseEntity.status(HttpStatus.OK).body(new SettingsResponse(
                settingsRepository.findById(1).orElseThrow().getValue().equals(YES),
                settingsRepository.findById(2).orElseThrow().getValue().equals(YES),
                settingsRepository.findById(3).orElseThrow().getValue().equals(YES)
        ));
    }

    public void putApiSettings(SettingsRequest settingsRequest) {

        User user = userRepository.getUserByEmail(personDetailsService.getCurrentUser()
                .getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with email %s not found",
                        personDetailsService.getCurrentUser().getEmail()))
        );

        final byte isModerator = 1;
        if (user.getIsModerator() != isModerator) {
            throw new RuntimeException("You are not a moderator!");
        }

        List<Boolean> globalSettings = new ArrayList<>();
        globalSettings.add(settingsRequest.isMULTIUSER_MODE());
        globalSettings.add(settingsRequest.isPOST_PREMODERATION());
        globalSettings.add(settingsRequest.isSTATISTICS_IS_PUBLIC());

         for (int i = 1; i <= 3; i++) {
             GlobalSettings settings = settingsRepository.findById(i).orElseThrow();
             settings.setValue(globalSettings.get(i-1) ? YES : NO);
             settingsRepository.save(settings);
         }

    }
}
