package com.example.coffee_project.config.account;

import com.example.coffee_project.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();


        http.authorizeRequests().antMatchers("/account/login").anonymous();
        http.authorizeRequests()
                // any role admin and employee
                //customer
                .antMatchers("/customer/showCreateForm").hasAnyAuthority("admin", "employee")
                .antMatchers("/customer/save").hasAnyAuthority("admin", "employee")
                .antMatchers("/customer/{id}/edit").hasAnyAuthority("admin", "employee")
                .antMatchers("/customer/{id}/delete").hasAuthority("admin")
                .antMatchers("/customer/update").hasAnyAuthority("admin", "employee")
                .antMatchers("/customer/{id}/view").hasAnyAuthority("admin", "employee")
                .antMatchers("/customer/list").hasAnyAuthority("admin", "employee")
                // user

                .antMatchers("/user/update").hasAnyAuthority("admin", "employee")
                .antMatchers("/user/update-form/{id}").hasAnyAuthority("admin", "employee")

                // product

                .antMatchers("/product").hasAnyAuthority("admin", "employee")
                .antMatchers("/product/search").hasAnyAuthority("admin", "employee")
                .antMatchers("/product/list").hasAnyAuthority("admin", "employee")
                .antMatchers("/product/detail/{id}").hasAnyAuthority("admin", "employee")
                .antMatchers("/user/create-form").hasAnyAuthority("admin", "employee")
                .antMatchers("/user/create").hasAnyAuthority("admin", "employee")

                //admin

                .antMatchers("/account/admin").hasAuthority("admin")


                .antMatchers("/product/edit/{id}").hasAuthority("admin")
                .antMatchers("/product/edit").hasAuthority("admin")
                .antMatchers("/product/delete").hasAuthority("admin")


                .antMatchers("/account/admin").hasAuthority("admin")


                .antMatchers("/user/delete").hasAuthority("admin")
                .antMatchers("/user/list").hasAuthority("admin")

        ;
        //
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/account/403");
        //    cấu hình login
        http.authorizeRequests().and()
                .formLogin()
                .loginProcessingUrl("/j_spring_security_check") // liên kết từ trang login
                .loginPage("/account/login")                           //trang login
                .defaultSuccessUrl("/product")                         //login thành công
                .failureUrl("/account/login?error=true")  // trang error
                .usernameParameter("accountName")                      //tham số
                .passwordParameter("accountPassword")
                .and().logout().logoutUrl("/account/logout").logoutSuccessUrl("/account/login");
        //
        http.authorizeRequests().and().rememberMe()
                .tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(24*60*60);//thời gian duy trì đăng nhập

    }

    public PersistentTokenRepository persistentTokenRepository(){
        InMemoryTokenRepositoryImpl memory=new InMemoryTokenRepositoryImpl();
        return memory;
    }
    //mã hóa code
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
        // so khớp mật khẩu đã mã hóa khi login
    }
}
