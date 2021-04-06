package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.YearsPostsResponse;
import com.skillbox.AndrewBlog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CalendarService {

    private final PostRepository postRepository;

    @Autowired
    public CalendarService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<?> getApiCalendarWithYear(int year) {

        Map<String, Integer> posts = new TreeMap<>();

            List<Integer> years = new ArrayList<>();
            years.add(year);

            List<String> dates = postRepository.getDatesWithActivePostsByYear(year).stream()
                    .sorted()
                    .collect(Collectors.toList());
            for (String date : dates) {
                posts.put(date, postRepository.getAmountOfPostsByDate(date));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new YearsPostsResponse(
                    years,
                    posts
            ));
    }

    public ResponseEntity<?> getApiCalendarAllYears() {

        Map<String, Integer> posts = new TreeMap<>();

            List<Integer> years = postRepository.getYearsWithActivePosts().stream()
                    .sorted()
                    .collect(Collectors.toList());

            List<String> dates = postRepository.getDatesWithActivePosts().stream()
                    .sorted()
                    .collect(Collectors.toList());
            for (String date : dates) {
                posts.put(date, postRepository.getAmountOfPostsByDate(date));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new YearsPostsResponse(
                    years,
                    posts
            ));
    }

    private List<Integer> getYears(Integer year) {
        return Stream.of(year)
                .filter(y -> y <= LocalDateTime.now().getYear())
                .collect(Collectors.toList());
    }

    private Map<String, Integer> getPosts (Integer year) {

        Map<String, Integer> posts = new TreeMap<>();
        List<String> dates = postRepository.getDatesWithActivePostsByYear(year);

        for (String date : dates) {
            posts.put(date, postRepository.getAmountOfPostsByDate(date));
        }
        return posts;
    }

}
