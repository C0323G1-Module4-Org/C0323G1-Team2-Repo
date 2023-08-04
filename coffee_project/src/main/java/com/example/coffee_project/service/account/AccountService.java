package com.example.coffee_project.service.account;

import com.example.coffee_project.model.account.Account;
import com.example.coffee_project.repository.account.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements IAccountService {
    @Autowired
    IAccountRepository accountRepository;

    @Override
    public Page<Account> getAllAccount(Pageable pageable, String searchName) {
        return accountRepository.findAllByAccountNameContaining(pageable, searchName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountRepository.findAccountByAccountName(username);
        if (account == null) {
            throw new UsernameNotFoundException("User " + account + " was not found in the database");
        }
        UserDetails userDetails = User.withUsername(account.getAccountName())
                .password(account.getAccountPassword())
                .authorities(new SimpleGrantedAuthority(account.getRole().getRoleName()))
                .build();
        return userDetails;
    }
}
