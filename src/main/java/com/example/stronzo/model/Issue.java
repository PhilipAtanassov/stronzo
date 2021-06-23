package com.example.stronzo.model;

import javax.persistence.*;

@Entity
@Table(name = "issues")
public class Issue {
    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    public Reporter author;

    @Column
    public String description;

    public Issue() {
    }

    public Issue(String description, Reporter author) {
        this.description = description;
        this.author = author;
    }
}
