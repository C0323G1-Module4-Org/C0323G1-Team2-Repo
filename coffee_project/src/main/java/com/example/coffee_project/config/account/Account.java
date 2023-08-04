package com.example.coffee_project.config.account;

import com.example.coffee_project.service.account.AccountService;
import com.example.coffee_project.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@Order(1)
public class Account extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

//        admin all role
        http.authorizeRequests().antMatchers("/account","/","/login","/logout").permitAll();

//        role employee
        http.authorizeRequests().antMatchers("/user").access("hasAnyRole('admin','employee')");
        http.authorizeRequests().antMatchers("/admin").access("hasRole('admin')");
//    cấu hình login
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/j_spring_security_check") // liên kết từ trang login
                .loginPage("/login")                              //trang login
                .defaultSuccessUrl("/account/admin")                         //login thành công
                .failureUrl("/account/login?error=true")  // trang error
                .usernameParameter("username")                      //tham số
                .passwordParameter("password")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
//
        http.authorizeRequests().and().rememberMe()
                .tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(24*60*60);//thời gian duy trì đăng nhập

    }

    public PersistentTokenRepository persistentTokenRepository(){
        InMemoryTokenRepositoryImpl memory=new InMemoryTokenRepositoryImpl();
        return memory;
    }
@Autowired
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
     @Autowired
    private IAccountService accountService;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //gọi userDetailsService
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
        // mã hóa mật khẩu

    }
}
