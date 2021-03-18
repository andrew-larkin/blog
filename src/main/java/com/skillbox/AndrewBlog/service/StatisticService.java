package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.StatisticResponse;
import com.skillbox.AndrewBlog.repository.PostRepository;
import com.skillbox.AndrewBlog.repository.PostVoteRepository;
import com.skillbox.AndrewBlog.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {

    private final SettingsRepository settingsRepository;
    private final PostRepository postRepository;
    private final PostVoteRepository postVoteRepository;

    @Autowired
    public StatisticService(SettingsRepository settingsRepository, PostRepository postRepository, PostVoteRepository postVoteRepository) {
        this.settingsRepository = settingsRepository;
        this.postRepository = postRepository;
        this.postVoteRepository = postVoteRepository;
    }

    public ResponseEntity<?> getApiStatisticsAll() {

        if (!settingsRepository.findById(3).get().getValue().equals("YES")) {
            //обработать логику текущего пользователя
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("You are not moderator, man");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new StatisticResponse(
                    postRepository.getAmountOfPosts(),
                    postVoteRepository.getAmountOfLikes(),
                    postVoteRepository.getAmountOfDisLikes(),
                    postRepository.getAmountOfViewCounts(),
                    postRepository.getDateOfFirstPublication().getTime()
            ));
        }
    }

    public ResponseEntity<?> getApiStatisticsMy() {

        return ResponseEntity.status(HttpStatus.OK).body("");

    }
}
