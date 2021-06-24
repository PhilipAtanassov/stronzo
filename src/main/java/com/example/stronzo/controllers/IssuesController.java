package com.example.stronzo.controllers;

import com.example.stronzo.dto.IssueDto;
import com.example.stronzo.errorhandling.DefaultControllerAdvice;
import com.example.stronzo.model.Issue;
import com.example.stronzo.model.Reporter;
import com.example.stronzo.service.IssuesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


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
    public Issue createIssue(@RequestBody @Valid IssueDto issue) {
        var toSave = new Issue(issue.description, new Reporter("me", "nowhere"));
        return issuesService.addIssue(toSave);
    }


    // not sure this is the right kind of exception to handle here
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultControllerAdvice.ErrorDto httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new DefaultControllerAdvice.ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                "Did you forget to add a body",
                "validation-failed",
                Map.of());
    }
}
