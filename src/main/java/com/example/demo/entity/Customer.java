package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Column(unique = true) // không được trùng
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;

    @JsonIgnore//không bắt nhập thông tin trên swagger
    boolean isDeleted = false;

    int loyaltyPoint;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

}
