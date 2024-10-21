package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Stylist {
    @Column(unique = true) // không được trùng
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;

    String description;

    @NotBlank(message = "Level cannot blank")
    int level;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    Account account;

}
