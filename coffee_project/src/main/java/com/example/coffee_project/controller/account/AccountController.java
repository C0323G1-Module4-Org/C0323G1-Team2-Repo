package com.example.coffee_project.controller.account;

import com.example.coffee_project.dto.account.AccountDto;
import com.example.coffee_project.model.account.Account;
import com.example.coffee_project.model.user.User;
import com.example.coffee_project.service.account.IAccountService;
import com.example.coffee_project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;


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
    private IAccountService accountService;
    @Autowired
    private IUserService userService;
    @GetMapping("/login")
    public ModelAndView login(Model model) {
        ModelAndView modelAndView = new ModelAndView("/login");
        AccountDto accountDto=new AccountDto();
        modelAndView.addObject("accountDto",accountDto);
        return modelAndView;
    }

    @PostMapping("/signup")
    public String signupAccount(@ModelAttribute AccountDto accountDto,RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg","Thêm mới thành công");
        accountService.save(accountDto);
        return "redirect:/account/admin";
    }
    @GetMapping("/admin")
    public  ModelAndView showListAccount(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "") String searchName) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("role.roleId").ascending());
        Page<Account> accountPage = accountService.getAllAccount(pageable, searchName);
        ModelAndView modelAndView = new ModelAndView("/account/list-account");
        modelAndView.addObject("accountDto", new AccountDto());
        modelAndView.addObject("accountPage", accountPage);
        modelAndView.addObject("searchName", searchName);
        return modelAndView;
    }

    @GetMapping("/403")
    public ModelAndView warring() {
        ModelAndView modelAndView = new ModelAndView("403");
        return modelAndView;
    }

    @GetMapping("/forgot-password")
    public ModelAndView showFormForgot() {
        ModelAndView modelAndView = new ModelAndView("forgot");
        return modelAndView;
    }

    @PostMapping("/forgot-password")
    public ModelAndView forgot(@RequestParam String username,
                               @RequestParam String email) {
        ModelAndView modelAndView = new ModelAndView("accuracy");
        User user = userService.findByName(username);
        System.out.println(user.getAccount().getAccountName());
        if (user != null) {
            if (user.getUserEmail().equals(email)) {
                System.out.println(email);
                Account account = accountService.findByUsername(username);
                String code=accountService.sendEmailAndReturnCode(user.getUserEmail());
                System.out.println(account.getAccountName());
                modelAndView.addObject("username", username);
                modelAndView.addObject("code",code);
                return modelAndView;
            } else {

            }
        }
        return new ModelAndView("/forgot");
    }

    @PostMapping("/reset-password")
    public String reset(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String password2,
                              @RequestParam String emailCode,
                              @RequestParam String code) {
        Account account=accountService.findByUsername(username);
        if (Objects.equals(password, password2) && Objects.equals(emailCode, code)){
           account.setAccountPassword(password);
            accountService.forgot(account);
            return "redirect:/account/login";
        }else {
            return"redirect:/account/forgot";
        }


    }
}
