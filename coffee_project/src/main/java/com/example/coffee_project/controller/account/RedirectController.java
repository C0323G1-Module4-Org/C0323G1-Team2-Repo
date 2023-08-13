package com.example.coffee_project.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("")
public class RedirectController {
    @GetMapping("")
    public String redirect() {
        return "redirect:/account/login";
    }
    @GetMapping("/login")
    public String redirectLogin() {
        return "redirect:/account/login";
    }

}
