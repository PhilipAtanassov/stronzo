package com.example.stronzo.dto;

import org.hibernate.validator.constraints.Length;

public class IssueDto {

    @Length(min = 4)
    public String description;

    public IssueDto() {
    }

    public IssueDto(String description) {
        this.description = description;
    }
}
