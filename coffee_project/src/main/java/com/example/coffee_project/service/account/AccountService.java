package com.example.coffee_project.service.account;

import com.example.coffee_project.dto.account.AccountDto;
import com.example.coffee_project.model.account.Account;
import com.example.coffee_project.model.account.Role;
import com.example.coffee_project.repository.account.IAccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;


@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private JavaMailSender javaMailSender;
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
    public void forgot(Account account) {
        String encoderPassword = bCryptPasswordEncoder.encode(account.getAccountPassword());
        account.setAccountPassword(encoderPassword);
        Role role = roleService.findByName("employee");
        account.setRole(role);
        accountRepository.save(account);
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findAccountByAccountName(username);
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public String sendEmailAndReturnCode(String to){
        // Sinh mã ngẫu nhiên
        String code = generateRandomCode(6); // Mã có độ dài 6
        // Tạo nội dung email
        String body = "Mã xác nhận của bạn là: " + code +" .Bạn vui lòng lấy mã để tại lại mật khẩu";
        // Cấu hình subject
        String subject="Queen Coffee gửi mã xác thực ta khoản của bạn";
        sendEmail(to,subject,body);
        return code;
    }

    @Override
    public void deleteAccount(Account account) {
        accountRepository.removeAccount(account.getAccountName());
    }

    private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountRepository.findAccountByAccountName(username);
        if (account == null) {
            throw new UsernameNotFoundException(" tài khoản " + username + " không có ");
        }
        UserDetails userDetails = User.withUsername(account.getAccountName())
                .password(account.getAccountPassword())
                .authorities(new SimpleGrantedAuthority(account.getRole().getRoleName()))
                .build();
        return userDetails;
    }

}
