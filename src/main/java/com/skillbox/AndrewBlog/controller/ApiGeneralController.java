package com.skillbox.AndrewBlog.controller;

import com.skillbox.AndrewBlog.api.request.ProfileRequest;
import com.skillbox.AndrewBlog.api.request.SettingsRequest;
import com.skillbox.AndrewBlog.api.response.InitResponse;
import com.skillbox.AndrewBlog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final TagsService tagsService;
    private final InitResponse initResponse;
    private final CalendarService calendarService;
    private final StatisticService statisticService;
    private final ProfileService profileService;

    @Autowired
    ApiGeneralController(SettingsService settingsService, TagsService tagsService, InitResponse initResponse,
                         CalendarService calendarService, StatisticService statisticService, ProfileService profileService) {
        this.settingsService = settingsService;
        this.tagsService = tagsService;
        this.initResponse = initResponse;
        this.calendarService = calendarService;
        this.statisticService = statisticService;
        this.profileService = profileService;
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
    private ResponseEntity<?> getApiCalendar(@RequestParam(required = false) int year) {
        return calendarService.getApiCalendar(year);
    }

    @GetMapping("/statistics/all")
    private ResponseEntity<?> getApiStatisticsAll() {
        return statisticService.getApiStatisticsAll();
    }

    //@Secured("USER")
    @PostMapping(value = "/profile/my", consumes = "multipart/form-data")
    private ResponseEntity<?> postApiProfileMy(@RequestParam(required = false) MultipartFile photo,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String email,
                                               @RequestParam(required = false) String password,
                                               @RequestParam(required = false) byte removePhoto) throws Exception {
        return profileService.postApiProfileMy(photo, name, email,
                password, removePhoto);
    }

    @PostMapping("profile/my")
    private ResponseEntity<?> postApiProfileMy(@RequestBody ProfileRequest profileRequest) {
        return profileService.postApiProfileMyWithoutPhoto(profileRequest);
    }

    //@Secured("USER")
    @GetMapping("/statistics/my")
    private ResponseEntity<?> getApiStatisticsMy() {
        return statisticService.getApiStatisticsMy();
    }

    //@Secured("MODERATOR")
    @PutMapping("/settings")
    private void putApiSettings(@RequestBody SettingsRequest settingsRequest) {
        settingsService.putApiSettings(settingsRequest);
    }



}
