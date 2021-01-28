package com.skillbox.AndrewBlog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillbox.AndrewBlog.api.request.EmailRequest;
import com.skillbox.AndrewBlog.api.response.ResultResponse;
import com.skillbox.AndrewBlog.model.ModerationStatus;
import com.skillbox.AndrewBlog.model.Post;
import com.skillbox.AndrewBlog.model.User;
import com.skillbox.AndrewBlog.repository.PostRepository;
import com.skillbox.AndrewBlog.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class CalendarTest {

    private User savedUser = null;
    //private Post savedPost = null;
    private final byte isActive = 1;

   /* Post postTest = new Post(
            isActive, ModerationStatus.ACCEPTED, 1, 3, new Date(System.currentTimeMillis() - 10000),
            "test title", "test text", 5);*/

    private User userTest = new User (new Date(System.currentTimeMillis()), "MikhailPolesov", "vahihi9345@boldhut.com",
            "12345678", null);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void saveTestPostToRep() {
        //savedPost = postRepository.save(postTest);
        savedUser = userRepository.save(userTest);
    }

    @AfterEach
    void restoreDB() {
        //postRepository.delete(savedPost);
        userRepository.delete(savedUser);
    }


    @Test
    void started() throws Exception {

        EmailRequest emailRequest = new EmailRequest("vahihi9345@boldhut.com");
        ResultResponse resultResponse = new ResultResponse(true);
        mvc.perform(post("api/auth/restore/")
        .content(objectMapper.writeValueAsString(emailRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(content().json(objectMapper.writeValueAsString(resultResponse)));
    }
}
