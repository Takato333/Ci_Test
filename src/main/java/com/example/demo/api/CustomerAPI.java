package com.example.demo.api;

import com.example.demo.entity.Customer;
import com.example.demo.model.CustomerRequest;
import com.example.demo.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*") //cho phép tất cả truy cập (CORS)
@SecurityRequirement(name = "api")

public class CustomerAPI {
    @Autowired
    CustomerService customerService;
    // Thêm 1 thằng sinh viên mới
    // /api/student => POST

    @PostMapping
    public ResponseEntity createCustomer(@Valid @RequestBody CustomerRequest customer) {
        Customer newCustomer = customerService.create(customer);
        return ResponseEntity.ok(newCustomer);
    }

    // Get danh sách sinh viên hiện tại
    @GetMapping
    public ResponseEntity getAllCustomer() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // /api/student/{studentId}
    @PutMapping("{customerId}")
    public ResponseEntity updateCustomer(@PathVariable long customerId, @Valid @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.update(customerId, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable long customerId) {
        Customer deletedCustomer = customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(deletedCustomer);
    }
}
