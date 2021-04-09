package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.YearsPostsResponse;
import com.skillbox.AndrewBlog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    private final PostRepository postRepository;

    @Autowired
    public CalendarService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<?> getApiCalendar(Integer year) {

        Map<String, Integer> posts = new TreeMap<>();
        List<Integer> years = postRepository.getYears();
        List<String> dates;
        if (year == null) {
           dates = postRepository.getDatesWithActivePosts().stream()
                    .sorted()
                    .collect(Collectors.toList());
            for (String date : dates) {
                posts.put(date, postRepository.getAmountOfPostsByDate(date));
            }

        } else {
            dates = postRepository.getDatesWithActivePostsByYear(year).stream()
                    .sorted()
                    .collect(Collectors.toList());
            for (String date : dates) {
                posts.put(date, postRepository.getAmountOfPostsByDate(date));
            }
        }

            return ResponseEntity.status(HttpStatus.OK).body(new YearsPostsResponse(
                    years,
                    posts
            ));
    }
}
