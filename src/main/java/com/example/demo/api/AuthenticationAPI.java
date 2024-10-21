package com.example.demo.api;

import com.example.demo.entity.Account;
import com.example.demo.model.*;
import com.example.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "api")
//@PreAuthorize("hasAuthority('MANAGER')") HÀM DÙNG ĐỂ PHÂN QUYỀN
public class AuthenticationAPI {

    //DI: Dependence Injection
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        //nhờ authenticationService => tạo dùm account
        AccountResponse newAccount = authenticationService.register(registerRequest);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<AccountResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        //nhờ authenticationService => tạo dùm account
        AccountResponse newAccount = authenticationService.login(loginRequest);
        return ResponseEntity.ok(newAccount);
    }

    @GetMapping("account")
    public ResponseEntity getAllAccount() {
        List<Account> accounts = authenticationService.getAllAccount();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("forgot-password")
    public ResponseEntity forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest ) {
        authenticationService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok("Forgot Password successfully");
    }

    @PostMapping("reset-password")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest ) {
        authenticationService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok("Reset Password successfully");
    }

}
