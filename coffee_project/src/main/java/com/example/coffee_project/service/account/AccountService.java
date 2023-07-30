package com.example.coffee_project.service.account;

import com.example.coffee_project.model.account.Account;
import com.example.coffee_project.repository.account.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService{
    @Autowired
    IAccountRepository accountRepository;
    @Override
    public Page<Account> getAllAccount(Pageable pageable,String searchName) {
        return accountRepository.findAllByAccountNameContaining(pageable,searchName);
    }
}
