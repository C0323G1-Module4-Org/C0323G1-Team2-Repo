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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;


@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserService userService;
    @ModelAttribute("accountDto")
    public AccountDto setUpLoginForm() {
        return new AccountDto();
    }
    @GetMapping("/login")
    public ModelAndView login(@ModelAttribute AccountDto accountDto) {
        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject("accountDto",accountDto);
        return modelAndView;
    }

    @PostMapping("/signup")
    public String signupAccount(@Valid @ModelAttribute AccountDto accountDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("msg", "Không để trống ");
        }else {
            if (accountService.findByUsername(accountDto.getAccountName())!=null){
                redirectAttributes.addFlashAttribute("msg","Tài khoản đã tồn tại");
            }else {
                redirectAttributes.addFlashAttribute("msg","Thêm mới thành công");
                accountService.save(accountDto);
            }
        }
        return "redirect:/account/admin";
    }
    @GetMapping("/admin")
    public  ModelAndView showListAccount(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "") String searchName) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("role.roleId").ascending());
        Page<Account> accountPage = accountService.getAllAccount(pageable, searchName,"ROLE_EMPLOYEE");
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
            }
        }
        return new ModelAndView("/forgot","msg","Kiểm tra lại");
    }

    @PostMapping("/reset-password")
    public String reset(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String password2,
                        @RequestParam String emailCode,
                        @RequestParam String code) {
        Account account=accountService.findByUsername(username);
        if (Objects.equals(password, password2) && Objects.equals(emailCode, code)) {
            account.setAccountPassword(password);
            accountService.forgot(account);
            return "redirect:/account/login";
        } else {
            return "redirect:/account/forgot";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String accountName,
                         RedirectAttributes redirectAttributes) {
        Account account = accountService.findByUsername(accountName);
        if (account.getRole().getRoleName().equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("msg", "Bạn không thể xóa tài khoản này");
        } else {
            accountService.deleteAccount(account);
            redirectAttributes.addFlashAttribute("msg", "Đã xóa tài khoản : " + account.getAccountName());
        }
        return "redirect:/account/admin";
    }

    @GetMapping("/change-password")
    public String showFormChange() {
        return "/account/change-pass";
    }

    @PostMapping("/change-password")
    public String changePass(@RequestParam("currentPassword") String currentPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("newPassword1") String newPassword1,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        if (Objects.equals(currentPassword, "") || Objects.equals(newPassword, "") || Objects.equals(newPassword1, "")) {
            redirectAttributes.addFlashAttribute("msg", "Vui lòng điền đầy đủ các thông tin");
        } else if (!Objects.equals(newPassword, newPassword1)) {
            redirectAttributes.addFlashAttribute("msg", "Kiểm tra lại nhập liệu");
        } else if (accountService.testPass(username, currentPassword)) {
            accountService.changePass(username, newPassword);
            redirectAttributes.addFlashAttribute("msg", "Đổi mật khẩu thành công");
            return "redirect:/order/";
        }else {
            redirectAttributes.addFlashAttribute("msg", "Kiểm tra lại nhập liệu");
        }
        redirectAttributes.addFlashAttribute("currentPassword", currentPassword);
        redirectAttributes.addFlashAttribute("newPassword", newPassword);
        redirectAttributes.addFlashAttribute("newPassword1", newPassword1);
        return "redirect:/account/change-password";
    }

}
