package com.skillbox.AndrewBlog.service;

import com.skillbox.AndrewBlog.api.response.StatisticResponse;
import com.skillbox.AndrewBlog.model.User;
import com.skillbox.AndrewBlog.repository.PostRepository;
import com.skillbox.AndrewBlog.repository.PostVoteRepository;
import com.skillbox.AndrewBlog.repository.SettingsRepository;
import com.skillbox.AndrewBlog.repository.UserRepository;
import com.skillbox.AndrewBlog.security.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {

    private final SettingsRepository settingsRepository;
    private final PostRepository postRepository;
    private final PostVoteRepository postVoteRepository;
    private final UserRepository userRepository;
    private final PersonDetailsService personDetailsService;

    @Autowired
    StatisticService(SettingsRepository settingsRepository, PostRepository postRepository, PostVoteRepository postVoteRepository, UserRepository userRepository, PersonDetailsService personDetailsService) {
        this.settingsRepository = settingsRepository;
        this.postRepository = postRepository;
        this.postVoteRepository = postVoteRepository;
        this.userRepository = userRepository;
        this.personDetailsService = personDetailsService;
    }

    public ResponseEntity<?> getApiStatisticsAll() {

        User user = getCurrentUser();
        byte isModerator = 1;

        if (user.getIsModerator() != isModerator || !settingsRepository.findById(3).orElseThrow()
                .getValue().toLowerCase().equals("YES")) {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                    .body("You don't have moderator's rights");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new StatisticResponse(
                    postRepository.getAmountOfPosts(),
                    postVoteRepository.getAmountOfLikes(),
                    postVoteRepository.getAmountOfDisLikes(),
                    postRepository.getAmountOfViewCounts(),
                    postRepository.getDateOfFirstPublication().getTime()/1000
            ));
        }
    }

    public ResponseEntity<?> getApiStatisticsMy() {

        User user = getCurrentUser();
        final byte LIKE = 1;
        final byte DISLIKE = -1;

        return ResponseEntity.status(HttpStatus.OK).body(new StatisticResponse(
                postRepository.countByUserId(user.getId()),
                postVoteRepository.countByUserIdValue(user.getId(), LIKE),
                postVoteRepository.countByUserIdValue(user.getId(), DISLIKE),
                postRepository.countAllViews(user.getId()),
                postRepository.getMyDateOfFirstPublication(user.getId()).getTime()/1000
        ));

    }

    private User getCurrentUser() {
        return userRepository.getUserByEmail(personDetailsService.getCurrentUser()
                .getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with email %s not found",
                        personDetailsService.getCurrentUser().getEmail()))
        );
    }
}
