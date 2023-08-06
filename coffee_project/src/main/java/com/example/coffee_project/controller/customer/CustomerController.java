package com.example.coffee_project.controller.customer;

import com.example.coffee_project.dto.customer.CustomerDto;
import com.example.coffee_project.model.customer.Customer;
import com.example.coffee_project.service.customer.ICustomerService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.text.DateFormat;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    //    Hiển thị danh sách khách hàng
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView showList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "") String searchName) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("customerName").ascending());
        Page<Customer> customerPage = customerService.findAll(pageable, searchName);
        ModelAndView modelAndView = new ModelAndView("customer/list");
        modelAndView.addObject("customerPage", customerPage);
        modelAndView.addObject("searchName", searchName);
        return modelAndView;
    }

    //    Tạo mới khách hàng
    @GetMapping("/showCreateForm")
    public String addNew(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        return "customer/create";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute CustomerDto customerDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        Customer customer = new Customer();
        new CustomerDto().validate(customerDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerDto", customerDto);
            return "customer/create";
        }
        customerDto.setCustomerPoint(0);
        BeanUtils.copyProperties(customerDto, customer);
        boolean check = customerService.save(customer);
        if (check) {
            redirectAttributes.addFlashAttribute("message", "Thêm mới thành công!");
            return "redirect:/customer/list";
        }
        return "customer/create";
    }

    //    Chỉnh sửa thông tin khách hàng
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        Customer customer = customerService.findById(id);
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        model.addAttribute("customerDto", customerDto);
        return "customer/edit";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute CustomerDto customerDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        Customer customer = new Customer();
        System.out.println(customerDto.getCustomerPoint());
        new CustomerDto().validate(customerDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerDto", customerDto);
            return "customer/edit";
        }
        BeanUtils.copyProperties(customerDto,customer);
        boolean result = customerService.update(customer.getCustomerId(), customer);
        if (result) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại!");
        }
        return "redirect:/customer/list";
    }

    //    Xóa khách hàng
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        boolean status = customerService.deleteById(id);
        if (status) {
            redirectAttributes.addFlashAttribute("message", "Xóa thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Xóa thất bại!");
        }
        return "redirect:/customer/list";
    }

    //  Chi tiết
    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customer/view";
    }

}
