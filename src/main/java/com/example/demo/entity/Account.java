package com.example.demo.entity;



import com.example.demo.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity //Thực thể dùng để tạo table trong MySQL
public class Account implements UserDetails {
    @Column(unique = true) // không được trùng
    @Id //khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    long id;

    @Enumerated(EnumType.STRING)
    Role role;

    @NotBlank(message = "Password can not blank!")
            @Size(min = 8, message = "Password must be at least 8 characters!")
    String password;

    @NotBlank(message = "Name can not blank!")
    String fullName;

    @Column(unique = true) // không được trùng
    @NotBlank(message = "Email can not blank!")
    @Email(message = "Email not valid!")
    String email;

    @Column(unique = true) // không được trùng
    @NotBlank(message = "Phone can not blank!")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Phone invalid!")
    String phone;

    String image;

    String sex;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(this.role != null) authorities.add(new SimpleGrantedAuthority(this.role.toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "account")
            @JsonIgnore
    List<Customer> customers;
}
