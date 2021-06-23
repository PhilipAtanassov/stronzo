package com.example.stronzo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class IssuesControllerTests {
    @Autowired
    MockMvc mvc;

    @Test
    public void getWithValidRoleReturnsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/issues")
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }
}
