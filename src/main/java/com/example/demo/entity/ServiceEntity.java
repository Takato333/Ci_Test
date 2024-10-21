package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.processing.Pattern;


@Data
@Entity
public class ServiceEntity {
    @Column(unique = true) // không được trùng
    @Id //khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;
    
    String name;

    String description;
    String type;

    @Min(value = 0, message = "Price must be positive")
    float price;
    int duration;
    int discount;
    String image;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    float totalPrice;

    @JsonIgnore//không bắt nhập thông tin trên swagger
    boolean isDeleted = false;
}
