package com.example.stronzo.service;

import com.example.stronzo.model.Issue;
import com.example.stronzo.repository.IssuesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// todo should there be an interface for this?
@Service
public class IssuesService {
    private IssuesRepository repo;

    public IssuesService(IssuesRepository repo) {
        this.repo = repo;
    }

    public List<Issue> getAllIssues() {
        var res = new ArrayList<Issue>();
        repo.findAll().iterator().forEachRemaining(res::add);
        return res;
    }

    public Issue addIssue(Issue issue) {
        return repo.save(issue);
    }
}
