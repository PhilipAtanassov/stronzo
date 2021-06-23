package com.example.stronzo.controllers;

import com.example.stronzo.dto.IssueDto;
import com.example.stronzo.model.Issue;
import com.example.stronzo.model.Reporter;
import com.example.stronzo.service.IssuesService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class IssuesController {
    private IssuesService issuesService;

    public IssuesController(IssuesService issuesService) {
        this.issuesService = issuesService;
    }

    @GetMapping("/issues")
    public List<Issue> getAllIssues() {
        return issuesService.getAllIssues();
    }

    @PutMapping("/issues")
    public Issue createIssue(@RequestBody IssueDto issue) {
        var toSave = new Issue(issue.description, new Reporter("me", "nowhere"));
        return issuesService.addIssue(toSave);
    }
}
