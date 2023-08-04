package com.example.coffee_project.service.account;

import com.example.coffee_project.model.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IAccountService extends UserDetailsService {
    Page<Account> getAllAccount(Pageable pageable, String searchName);
}
