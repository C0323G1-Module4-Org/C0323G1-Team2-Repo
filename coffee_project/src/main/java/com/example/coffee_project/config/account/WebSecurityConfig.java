package com.example.coffee_project.config.account;

import com.example.coffee_project.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
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
        http.authorizeRequests().antMatchers("/account/forgot-password","/account/reset-password").permitAll();
        // đổi mk
        http.authorizeRequests().antMatchers("/account/change-password").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE");

        http.authorizeRequests()
                // any role admin and employee
                //customer
                .antMatchers("/customer/showCreateForm").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/customer/save").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/customer/{id}/edit").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/customer/{id}/delete").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/customer/update").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/customer/{id}/view").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/customer/list").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                // user
                .antMatchers("/user/create-form").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/user/create").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/user/update").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/user/update-form/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")

                // product

                .antMatchers("/product").hasAuthority("ROLE_ADMIN")
                .antMatchers("/product/searchCard").hasAuthority("ROLE_ADMIN")
                .antMatchers("/product/list").hasAuthority("ROLE_ADMIN")
                .antMatchers("/product/detail/{id}").hasAuthority("ROLE_ADMIN")



                //order
                .antMatchers("/order/").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/order/add-order").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/order/delete/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/order/payment").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/order/revenue").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                .antMatchers("/order/confirm-payment").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")

                //admin

                .antMatchers("/account/admin").hasAuthority("ROLE_ADMIN")


                .antMatchers("/product/edit/{id}").hasAuthority("ROLE_ADMIN")
                .antMatchers("/product/edit").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST,"/product/delete").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET,"/product/delete").hasAuthority("ROLE_ADMIN")
                .antMatchers("/product/create").hasAuthority("ROLE_ADMIN")


                .antMatchers("/account/admin").hasAuthority("ROLE_ADMIN")
                .antMatchers("/account/delete").hasAuthority("ROLE_ADMIN")


                .antMatchers("/user/delete").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user/change-form/{id}").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user/change").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user/list").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user/new-employee").hasAuthority("ROLE_ADMIN")

        ;
        //
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/account/403");
        //    cấu hình login
        http.authorizeRequests().and()
                .formLogin()
                .loginProcessingUrl("/j_spring_security_check") // liên kết từ trang login
                .loginPage("/account/login")                           //trang login
                .defaultSuccessUrl("/order/")                         //login thành công
                .failureUrl("/account/login?error=true")  // trang error
                .usernameParameter("accountName")                      //tham số
                .passwordParameter("accountPassword")
                .and().logout().invalidateHttpSession(true).logoutUrl("/account/logout").logoutSuccessUrl("/account/login");
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
