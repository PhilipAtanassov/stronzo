package com.example.stronzo.model;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "reporters")
public class Reporter {
    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "name")
    public String name;

    public Reporter() {
    }

    public Reporter(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Column(name = "address")
    public String address;
}
