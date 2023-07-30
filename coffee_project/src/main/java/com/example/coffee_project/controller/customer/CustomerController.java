package com.example.coffee_project.controller.customer;

import com.example.coffee_project.model.customer.Customer;
import com.example.coffee_project.service.customer.ICustomerService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return modelAndView;
    }

    //    Tạo mới khách hàng
    @GetMapping("/showCreateForm")
    public String addNew(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @PostMapping("/save")
    public String save(Customer customer, RedirectAttributes redirectAttributes) {
        boolean result = customerService.save(customer);
        if (result) {
            redirectAttributes.addFlashAttribute("message", "Thêm mới thành công!");
        } else {
            redirectAttributes.addAttribute("message", "Thêm mới thất bại!");
        }
        return "redirect:customer/list";
    }

}
