package com.skillbox.AndrewBlog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillbox.AndrewBlog.model.ModerationStatus;
import com.skillbox.AndrewBlog.model.Post;
import com.skillbox.AndrewBlog.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class CalendarTest {

    private Post savedPost = null;
    private final byte isActive = 1;

    Post postTest = new Post(
            isActive, ModerationStatus.ACCEPTED, 1, 3, new Date(System.currentTimeMillis() - 10000),
            "test title", "test text", 5);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

  /*  @BeforeEach
    public void saveTestPostToRep() {
        savedPost = postRepository.save(postTest);
    }

    @AfterEach
    public void restoreBd() {
        postRepository.delete(savedPost);
    }*/

    @Test
    void started() {
        List<Integer> years = new ArrayList<>();
        years.add(2020);

        assertEquals(years, postRepository.getYears());
     assertEquals(3, postRepository.getDates().size());
     assertEquals(3, postRepository.getDatesByYear("2020").size());
    }
}
