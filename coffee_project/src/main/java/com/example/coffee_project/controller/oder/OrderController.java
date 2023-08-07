package com.example.coffee_project.controller.oder;

import com.example.coffee_project.dto.oder.OrderDetailDto;
import com.example.coffee_project.model.customer.Customer;
import com.example.coffee_project.model.oder.Order;
import com.example.coffee_project.model.oder.OrderDetail;
import com.example.coffee_project.model.product.Product;
import com.example.coffee_project.model.user.User;
import com.example.coffee_project.service.customer.ICustomerService;
import com.example.coffee_project.service.oder.IOrderDetailService;
import com.example.coffee_project.service.oder.IOrderService;
import com.example.coffee_project.service.product.IProductService;
import com.example.coffee_project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IProductService productService;
    @Autowired
    ICustomerService customerService;
    @Autowired
    IUserService userService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderDetailService orderDetailService;

    @GetMapping(value = "/")
    public ModelAndView home(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String name) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("productName").ascending());
        Page<Product> listProduct = productService.searchByName(pageable, name);
        ModelAndView modelAndView = new ModelAndView("oder/index");
        Order order = orderService.findCurrentOrder(true);
        if (listProduct.getTotalPages() > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= listProduct.getTotalPages(); i++) {
                pageNumbers.add(i);
            }
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        if (order != null) {
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            orderDetailDto.setOrderDetailList(new ArrayList<>(order.getOrderDetailSet()));
            modelAndView.addObject("listOrderDetail", orderDetailDto);
        }
        modelAndView.addObject("name", name);
        modelAndView.addObject("listProduct", listProduct);
        return modelAndView;
    }

    @PostMapping("/add-order")
    public String addOrder(@RequestParam(required = false, defaultValue = "-1") int quantity, @RequestParam int idProduct, RedirectAttributes redirectAttributes) {
        User user = userService.findByID(1);
        Order order = orderService.findCurrentOrder(true);
        Product product = productService.findProductById(idProduct);
        if (quantity > 0) {
            if (order == null) {
                order = orderService.save(new Order(true, new Timestamp(new Date().getTime()), user));
            }
            OrderDetail orderDetail = orderDetailService.findByOrderAndProduct(order, product);
            if (orderDetail != null) {
                orderDetail.setQuantityProduct(orderDetail.getQuantityProduct() + quantity);
                orderDetailService.save(orderDetail);
            } else {
                orderDetailService.save(new OrderDetail(quantity, product.getProductPrice(), product, order));
            }
            redirectAttributes.addFlashAttribute("msg", "Đã đặt thành công");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Đặt thất bại");
        }
        return "redirect:/order/";
    }


    @GetMapping("/delete/{id}")
    public String deleteOrderDetail(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Order order = orderService.findCurrentOrder(true);
        if (order != null) {
            orderDetailService.remove(id);
            redirectAttributes.addFlashAttribute("msg1", "Đã xóa thành công sản phẩm");
        } else
            redirectAttributes.addFlashAttribute("msg1", "xóa sản phẩm thất bại");
        return "redirect:/order/";
    }

    @PostMapping("/payment")
    public ModelAndView Payment(@ModelAttribute OrderDetailDto orderDetailDto) {
        ModelAndView modelAndView = new ModelAndView("oder/payment");
        Order order = orderService.findCurrentOrder(true);
        List<Customer> listCustomer = customerService.findListCustomer();
        if (orderDetailDto != null) {
            for (OrderDetail o : orderDetailDto.getOrderDetailList()) {
                orderDetailService.save(o);
            }
        }
        modelAndView.addObject("listCustomer", listCustomer);
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @PostMapping("/confirm-payment")
    public String confirmPayment(@RequestParam String phoneNumber) {
        Order order = orderService.findCurrentOrder(true);
        double totalPrice = 0;
        for (OrderDetail orderDetail : order.getOrderDetailSet()) {
            totalPrice += (orderDetail.getProductPrice() * orderDetail.getQuantityProduct());
        }
        return "redirect:/order/";
    }
}
