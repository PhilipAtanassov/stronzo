package com.example.stronzo.repository;

import com.example.stronzo.model.Issue;
import org.springframework.data.repository.CrudRepository;

public interface IssuesRepository extends CrudRepository<Issue, Long> {

}
