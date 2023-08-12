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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
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
    public Page<Account> getAllAccount(Pageable pageable, String searchName,String roleName) {
        return accountRepository.findAllByAccountNameContainingAndRole_RoleName(pageable, searchName,roleName);
    }

    @Override
    public void save(AccountDto accountDto) {

        String encoderPassword = bCryptPasswordEncoder.encode("12345");
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        account.setAccountPassword(encoderPassword);
        Role role = roleService.findByName("ROLE_EMPLOYEE");
        System.out.println(role);
        account.setRole(role);
        accountRepository.save(account);
    }
    @Override
    public void forgot(Account account) {
        String encoderPassword = bCryptPasswordEncoder.encode(account.getAccountPassword());
        account.setAccountPassword(encoderPassword);
        Role role = roleService.findByName("ROLE_EMPLOYEE");
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
        String code = generateRandomCode(4); // Mã có độ dài 4
        // Tạo nội dung email
        String body = "<fieldset style=\"border: 2px solid sandybrown;color: darkslategray;background-image:url('https://i.pinimg.com/550x/ef/ff/ce/efffcecb709c4ac7cf5e536694f2ea99.jpg');height: 320px;width:500px\">\n" +
                "    <legend style=\"text-align: center\">\n" +
                "        <h1 style=\"color: sandybrown\">Queen Coffee</h1>\n" +
                "    </legend>\n" +
                "    <legend>\n" +
                "        <h4>\n" +
                "            Chào bạn\n" +
                "            <br/>\n" +
                "            <br/>\n" +
                "            Chúng tôi gửi bạn mã xác nhận tài khoản của bạn tại Queen coffee <br/><br/>\n" +
                "\n" +
                "            Mã xác nhận của bạn là : " + code + "\n" +
                "        </h4>\n" +
                "        <h4>\n" +
                "            Bạn không chia sẻ mã trên cho bất kì ai !!!\n" +
                "        </h4>\n" +
                "        <h4>Trân trọng !!!</h4>\n" +
                "    </legend>\n" +
                "</fieldset>";
        // Cấu hình subject
        String subject = "Queen Coffee trân trọng thông báo!!!";
        sendEmail(to, subject, body);
        return code;
    }

    @Override
    public void deleteAccount(Account account) {
        accountRepository.removeAccount(account.getAccountName());
    }

    @Override
    public boolean testPass(String username, String pass) {
        Account account = accountRepository.findAccountByAccountName(username);
        System.out.println(username);
        String oldPassEncoder=account.getAccountPassword();
        boolean isMath= bCryptPasswordEncoder.matches(pass,oldPassEncoder);
        System.out.println(isMath);
        return isMath;
    }

    @Override
    public void changePass(String username, String newPassword) {
        Account account = accountRepository.findAccountByAccountName(username);
        account.setAccountPassword(bCryptPasswordEncoder.encode(newPassword));
        accountRepository.save(account);
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
        List<GrantedAuthority> grantList = new ArrayList<>();

        grantList.add(new SimpleGrantedAuthority(account.getRole().getRoleName()));
        UserDetails userDetails =  new User(account.getAccountName(),
                account.getAccountPassword(), grantList);
        return userDetails;
    }

}
