package com.example.demo.repository;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    //tim sccount by username
    Account findAccountByPhone(String phone);

    Account findAccountById(long id);

    Account findAccountByEmail(String email);

}
