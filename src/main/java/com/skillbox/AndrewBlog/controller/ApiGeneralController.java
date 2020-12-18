package com.Skillbox.AndrewBlog.controller;

import com.Skillbox.AndrewBlog.api.response.InitResponse;
import com.Skillbox.AndrewBlog.api.response.SettingsResponse;
import com.Skillbox.AndrewBlog.api.response.TagsResponse;
import com.Skillbox.AndrewBlog.service.SettingsService;
import com.Skillbox.AndrewBlog.service.TagsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final TagsService tagsService;
    private final InitResponse initResponse;

    public ApiGeneralController(SettingsService settingsService, TagsService tagsService, InitResponse initResponse) {
        this.settingsService = settingsService;
        this.tagsService = tagsService;
        this.initResponse = initResponse;
    }

    @GetMapping("/settings")
    private ResponseEntity<SettingsResponse> settings () {
        final SettingsResponse globalSettings = settingsService.getGlobalSettings();
        return new ResponseEntity<>(globalSettings, HttpStatus.OK);
    }

    @GetMapping("/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/tag")
    private ResponseEntity<TagsResponse> getTags(@RequestParam String request) {
        final TagsResponse tagsResponse;
        if (request == null || request.equals("")) {
            tagsResponse = tagsService.getTags();
        } else {
            tagsResponse = tagsService.getTags(request);
        }
        return new ResponseEntity<>(tagsResponse, HttpStatus.OK);
    }
}
