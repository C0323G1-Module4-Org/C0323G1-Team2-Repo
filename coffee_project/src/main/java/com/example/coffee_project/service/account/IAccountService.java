package com.example.coffee_project.service.account;

import com.example.coffee_project.model.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAccountService {
    Page<Account> getAllAccount(Pageable pageable, String searchName);
}
