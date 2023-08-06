package com.example.coffee_project.service.account;

import com.example.coffee_project.dto.account.AccountDto;
import com.example.coffee_project.model.account.Account;
import com.example.coffee_project.model.account.Role;
import com.example.coffee_project.repository.account.IAccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IRoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Override
    public Page<Account> getAllAccount(Pageable pageable, String searchName) {
        return accountRepository.findAllByAccountNameContaining(pageable, searchName);
    }

    @Override
    public void save(AccountDto accountDto) {

        String encoderPassword = bCryptPasswordEncoder.encode("12345");
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        account.setAccountPassword(encoderPassword);
        Role role = roleService.findByName("employee");
        System.out.println(role);
        account.setRole(role);
        accountRepository.save(account);
    }
    @Override
    public Account findByUsername(String username){
        return accountRepository.findAccountByAccountName(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountRepository.findAccountByAccountName(username);
        if (account == null) {
            throw new UsernameNotFoundException(" tài khoản " + account + " không có ");
        }
        UserDetails userDetails = User.withUsername(account.getAccountName())
                .password(account.getAccountPassword())
                .authorities(new SimpleGrantedAuthority(account.getRole().getRoleName()))
                .build();
        return userDetails;
    }

}
