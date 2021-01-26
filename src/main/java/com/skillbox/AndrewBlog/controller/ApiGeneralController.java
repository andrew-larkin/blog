package com.skillbox.AndrewBlog.controller;

import com.skillbox.AndrewBlog.api.response.InitResponse;
import com.skillbox.AndrewBlog.service.CalendarService;
import com.skillbox.AndrewBlog.service.SettingsService;
import com.skillbox.AndrewBlog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final CalendarService calendarService;

    @Autowired
    ApiGeneralController(SettingsService settingsService, TagsService tagsService, InitResponse initResponse,
                         CalendarService calendarService) {
        this.settingsService = settingsService;
        this.tagsService = tagsService;
        this.initResponse = initResponse;
        this.calendarService = calendarService;
    }

    @GetMapping("/settings")
    private ResponseEntity<?> getApiSettings () {
        return settingsService.getApiSettings();
    }

    @GetMapping("/init")
    private InitResponse getApiInit() {
        return initResponse;
    }

    @GetMapping("/tag")
    private ResponseEntity<?> getApiTag(@RequestParam(required = false) String query) {
        return tagsService.getApiTag(query);
    }

    @GetMapping("/calendar")
    private ResponseEntity<?> getApiCalendar(@RequestParam(required = false) String year) {
        return calendarService.getApiCalendar(year);
    }

}
