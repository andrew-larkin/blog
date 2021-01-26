package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.ErrorDescriptionResponse;
import com.skillbox.AndrewBlog.api.response.YearsPostsResponse;
import com.skillbox.AndrewBlog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CalendarService {

    private final PostRepository postRepository;

    @Autowired
    public CalendarService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<?> getApiCalendar(String year) {

        if (year != null) {
            StringBuilder errors = new StringBuilder();
            if (Integer.parseInt(year) < 2020 || Integer.parseInt(year) > LocalDateTime.now().getYear()) {
                errors.append("'year' should be greater than 2020 and less then ").append(LocalDateTime.now().getYear()).append(". ");
            }

            if (!errors.toString().equals("")) {
                return ResponseEntity.status(200).body(new ErrorDescriptionResponse(errors.toString().trim()));
            }

        }
        return ResponseEntity.status(HttpStatus.OK).body(new YearsPostsResponse(
                getYears(year),
                getPosts(year)
        ));
    }

    private List<Integer> getYears(String year) {

        List<Integer> years = new ArrayList<>();
        if (year != null) {
            years.add(Integer.parseInt(year));
            return years;
        }

        return postRepository.getYears();
    }

    private Map<String, Integer> getPosts (String year) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Integer> posts = new TreeMap<>();
        if (year == null) {
            for (Date date : postRepository.getDates()) {
                posts.put(simpleDateFormat.format(date), postRepository
                        .getDatesCount(date));
            }
        } else {
            for (Date date : postRepository.getDatesByYear(year)) {
                posts.put(simpleDateFormat.format(date), postRepository
                        .getDatesCount(date));
            }
        }
        return posts;
    }

}
