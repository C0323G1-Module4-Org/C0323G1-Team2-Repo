package com.example.coffee_project.controller.account;

import com.example.coffee_project.dto.account.AccountDto;
import com.example.coffee_project.model.account.Account;
import com.example.coffee_project.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/account")
public class AccountController {
    @ModelAttribute("role")
    public String showRole(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String role=authentication.getAuthorities().toString();
        System.out.println(role);
        return role;
    }
    @Autowired
    IAccountService accountService;
    @GetMapping("/login")
    public ModelAndView login(Model model) {
        ModelAndView modelAndView = new ModelAndView("/login");
        AccountDto accountDto=new AccountDto();
        modelAndView.addObject("accountDto",accountDto);
        return modelAndView;
    }
    //    @PostMapping("/login")
//    public ModelAndView loginAccount(@ModelAttribute AccountDto accountDto) {
//     if (accountService.equals(accountDto)){
//
//     }
//     return new ModelAndView("/login");
//    }
    @GetMapping("/signup")
    public ModelAndView signup(){
        ModelAndView modelAndView =new ModelAndView("signup");
        AccountDto accountDto=new AccountDto();
        modelAndView.addObject("accountDto",accountDto);
        return modelAndView;
    }

    @PostMapping("/signup")
    public String signupAccount(@ModelAttribute AccountDto accountDto,
                                RedirectAttributes redirectAttributes) {
        accountService.save(accountDto);
        return "redirect:/account/admin";
    }
    @GetMapping("/admin")
    public  ModelAndView showListAccount(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "")String searchName){
        Pageable pageable= PageRequest.of(page, 10,Sort.by("role.roleId").ascending());
        Page<Account> accountPage=accountService.getAllAccount(pageable,searchName);
        ModelAndView modelAndView=new ModelAndView("/account/list-account");
        modelAndView.addObject("accountDto",new AccountDto());
        modelAndView.addObject("accountPage",accountPage);
        modelAndView.addObject("searchName",searchName);
        return modelAndView;
    }
    @GetMapping("/403")
    public ModelAndView warring(){
    ModelAndView modelAndView = new ModelAndView("403");
    return modelAndView;
    }
    @GetMapping("/logout")
    public ModelAndView logout(){
        ModelAndView modelAndView=new ModelAndView("/login");
       return modelAndView;
    }
}
