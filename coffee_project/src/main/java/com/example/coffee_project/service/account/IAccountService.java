package com.example.coffee_project.service.account;

import com.example.coffee_project.dto.account.AccountDto;
import com.example.coffee_project.model.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService {
    Page<Account> getAllAccount(Pageable pageable, String searchName,String roleName);
    void save(AccountDto accountDto);
    void forgot(Account account);
    Account findByUsername(String username);
    void sendEmail(String to, String subject, String body);
     String sendEmailAndReturnCode(String to);

    void deleteAccount(Account account);
    boolean testPass(String username,String pass);

    void changePass(String username,String newPassword);
}
