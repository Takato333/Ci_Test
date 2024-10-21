package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.CustomerRequest;
import com.example.demo.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;

    //Create
    public Customer create(CustomerRequest customerRequest) {
    //add customer vào database bằng repository
        try {
            Customer customer = modelMapper.map(customerRequest, Customer.class);

            //Xác định account nào tạo cái customer này
            //thông qua đc cái filter r
            //lưu lại đc cái account yêu cầu tạo student này r
            Account accountRequest = authenticationService.getCurrentAccount();
            customer.setAccount(accountRequest);

            Customer newCustomer = customerRepository.save(customer);
            return newCustomer;//trả về 1 thằng mới
        }catch (Exception e){
            throw new DuplicateEntity("Duplicate! Fail to create!");
        }
    }

    //Read
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findCustomerByIsDeletedFalse();
        return customers;
    }

    //Update
    public Customer update(long id, Customer customer) {
        //bước 1: tìm ra student cần đc update
        Customer oldCustomer = customerRepository.findCustomerById(id);

        if(oldCustomer == null) throw new EntityNotFoundException("Customer not found!");
        // => có tồn tại

        //bước 2: cập nhật thông tin của nó
        oldCustomer.setLoyaltyPoint(customer.getLoyaltyPoint());

        //bước 3: lưu xuống database
        return customerRepository.save(oldCustomer);
    }

    //Delete
    public Customer deleteCustomer(long id) {
        Customer oldCustomer = getCustomerById(id);
        oldCustomer.setDeleted(true);
        return customerRepository.save(oldCustomer);
    }

    public Customer getCustomerById(long id) {
        Customer oldCustomer = customerRepository.findCustomerById(id);
        if(oldCustomer == null) throw new EntityNotFoundException("Customer not found");
        //if user.status == "BLOCK" ==> throw new EntityNotFoundException("Student not found");
        return oldCustomer;
    }
}
