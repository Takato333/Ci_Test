package com.example.demo.model;

import com.example.demo.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data //getter setter
public class RegisterRequest {
    @Column(unique = true) // không được trùng
    @NotBlank(message = "Phone can not blank!")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Phone invalid!")
    String phone;

    @NotBlank(message = "Password can not blank!")
    @Size(min = 8, max = 16, message = "Password must be at least 8 and less than 16 characters!")
    String password;

    @NotBlank(message = "Name can not blank!")
    String fullName;

    @Column(unique = true) // không được trùng
    @NotBlank(message = "Email can not blank!")
    @Email(message = "Email not valid!")
    String email;

    String image;

    String sex;

    Role role;
}
