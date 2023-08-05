package com.example.coffee_project.controller.user;

import com.example.coffee_project.dto.user.UserDto;
import com.example.coffee_project.model.user.User;
import com.example.coffee_project.service.user.IEmployeeTypeService;
import com.example.coffee_project.service.user.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IEmployeeTypeService employeeTypeService;

    @GetMapping("/list")
    public ModelAndView showListUser(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "") String search) {
        ModelAndView modelAndView = new ModelAndView("/user/list");
        Pageable pageable = PageRequest.of(page, 3,
                Sort.by("userName").ascending().and(Sort.by("userSalary").ascending()));

        Page<User> userPage = userService.findAll(pageable, search);
        System.out.println(userPage.getTotalElements());
        modelAndView.addObject("userPage", userPage);
        modelAndView.addObject("search", search);
        return modelAndView;
    }

    @GetMapping("/create-form")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("user/create");
        UserDto userDto = new UserDto();
        modelAndView.addObject("userDto", userDto);
        modelAndView.addObject("employeeTypeList", employeeTypeService.findAll());
        return modelAndView;
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute UserDto userDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        new UserDto().validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);

            model.addAttribute("employeeTypeList", employeeTypeService.findAll());
            return "/user/create";
        }
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setUserSalary(0D);
        userService.saveUser(user);
        redirectAttributes.addAttribute("success",
                "Thêm thông tin cho tài khoản " + user.getAccount() + " thành công");
        return "redirect:/user/list";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Integer userId,
                             @RequestParam String userName,
                             RedirectAttributes redirectAttributes) {
        System.out.println(userName);
        userService.removeUser(userId);
        redirectAttributes.addFlashAttribute("deleteSuccess", userName);
        return "redirect:/user/list";
    }

    @GetMapping("/update-form/{id}")
    public String showUpdateUserForm(@PathVariable Integer id,
                                     Model model){
        User user = userService.findByID(id);
        if(user == null){
            return "redirect:/user/create-form";
        }
        model.addAttribute("employeeTypeList", employeeTypeService.findAll());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        model.addAttribute("userDto",userDto);
        return "/user/update";
    }
    @GetMapping("/detail/{id}")
    public ModelAndView showUserInfo(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("user/detail");
        User user = userService.findByID(id);
        modelAndView.addObject("user",user);
        return modelAndView;
    }
    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute UserDto userDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        new UserDto().validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);
            model.addAttribute("employeeTypeList", employeeTypeService.findAll());
            return "/user/update";
        }
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        userService.saveUser(user);
        redirectAttributes.addAttribute("success",
                "Sửa tài khoản " + user.getAccount() + " thành công");
        return "redirect:/user/list";
    }
}
