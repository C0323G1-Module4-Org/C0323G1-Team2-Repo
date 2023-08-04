package com.example.coffee_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CoffeeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeProjectApplication.class, args);
        BCryptPasswordEncoder cryptPasswordEncoder=new BCryptPasswordEncoder();

        System.out.println(cryptPasswordEncoder.encode("12345"));
    }

}
