package com.example.demo.model;

import lombok.Data;

@Data
public class ServiceRequest {
    String name;
    String description;
    String type;
    float price;
    int duration;
    int discount;
    String image;
}
