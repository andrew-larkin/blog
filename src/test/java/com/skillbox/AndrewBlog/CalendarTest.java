package com.skillbox.AndrewBlog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class CalendarTest {

    private final byte isActive = 1;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenEmptyYear_ThenRetrieve() throws Exception {

        assertEquals(1, isActive);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/calendar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.years[:1]")
                .value(2020));
    }

    @Test
    public void givenYear_ThenRetrieve() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/calendar")
        .param("year", String.valueOf(2021)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.years[:1]")
                        .value(2020))
        .andExpect(jsonPath("$.posts.2020-11-15")
        .value(2));
    }


}
