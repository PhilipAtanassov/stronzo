package com.example.stronzo.controllers;

import com.example.stronzo.dto.IssueDto;
import com.example.stronzo.repository.IssuesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class IssuesControllerTests {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @Autowired
    IssuesRepository repo;

    @BeforeEach
    public void nukeTheRepo() {
        repo.deleteAll();
    }

    @Test
    public void getIssuesReturnsAnEmptyArray() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/issues"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    public void createsNewIssue() throws Exception {
        var issue = new IssueDto("desc");
        var body = om.writeValueAsString(issue);
        // create an issue
        mvc.perform(
                MockMvcRequestBuilders.put("/issues")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(body));

        // get all issues to verify the one we created is there
        mvc.perform(MockMvcRequestBuilders.get("/issues"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        om.writeValueAsString(Collections.singletonList(issue))));

    }

    @Test
    public void failsOnInvalidIssue() throws Exception {
        var issueWithTooShortDescription = new IssueDto("d");
        var body = om.writeValueAsString(issueWithTooShortDescription);
        mvc.perform(
                MockMvcRequestBuilders.put("/issues")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.moreDetails.description").value("length must be between 4 and 2147483647"));
    }


    @Test
    public void failsOnEmptyBody() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.put("/issues")
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Did you forget to add a body"));
    }

}
