package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //customize
    //đặt tên function theo định dạng DataJPA cung cấp
    //findStudentById(long id)
    Customer findCustomerById(long id);
    List<Customer> findCustomerByIsDeletedFalse();

}
