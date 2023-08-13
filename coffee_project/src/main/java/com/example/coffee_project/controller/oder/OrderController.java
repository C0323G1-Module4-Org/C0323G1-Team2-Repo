package com.example.coffee_project.controller.oder;

import com.example.coffee_project.dto.oder.OrderDetailDto;
import com.example.coffee_project.model.customer.Customer;
import com.example.coffee_project.model.oder.Order;
import com.example.coffee_project.model.oder.OrderDetail;
import com.example.coffee_project.model.product.Product;
import com.example.coffee_project.model.product.ProductType;
import com.example.coffee_project.model.user.User;
import com.example.coffee_project.service.customer.ICustomerService;
import com.example.coffee_project.service.oder.IOrderDetailService;
import com.example.coffee_project.service.oder.IOrderService;
import com.example.coffee_project.service.product.IProductService;
import com.example.coffee_project.service.product.IProductTypeService;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IProductService productService;
    @Autowired
    IProductTypeService productTypeService;
    @Autowired
    ICustomerService customerService;
    @Autowired
    IUserService userService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderDetailService orderDetailService;

    @GetMapping(value = "/")
    public String home(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String name, Model model, @RequestParam(defaultValue = "") String type) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(authentication.getName());
        List<Product> bestSeller = productService.getBestSeller();
        if (user == null)
            return "redirect:/user/create-form";
        Order order = orderService.findCurrentOrder(true, user);
        Pageable pageable = PageRequest.of(page, 8, Sort.by("product_name").ascending());
        Page<Product> listProduct = productService.searchByNameAndProductType(pageable, "%" + name + "%", "%" + type + "%");
        if (listProduct.isEmpty())
            model.addAttribute("msg2", "Không có sản phẩm nào được tìm thấy");
        if (order != null) {
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            orderDetailDto.setOrderDetailList(new ArrayList<>(order.getOrderDetailSet()));
            model.addAttribute("listOrderDetail", orderDetailDto);
        }
        model.addAttribute("listProductType", productTypeService.display());
        model.addAttribute("bestSeller", bestSeller);
        model.addAttribute("name", name);
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("type", type);
        return "oder/index";
    }

    @PostMapping("/add-order")
    public String addOrder(@RequestParam(required = false, defaultValue = "-1") int quantity, @RequestParam int idProduct, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(authentication.getName());
        if (!productService.isExitProduct(idProduct)) {
            redirectAttributes.addFlashAttribute("msg", "Sản phẩm không có trong kho");
        } else {
            Order order = orderService.findCurrentOrder(true, user);
            Product product = productService.findProductById(idProduct);
            if (quantity > 0 && quantity < 101) {
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
                redirectAttributes.addFlashAttribute("msg", "Số lượng vượt giới hạn");
            }
        }
        return "redirect:/order/";
    }


    @GetMapping("/delete/{id}")
    public String deleteOrderDetail(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(authentication.getName());
        Order order = orderService.findCurrentOrder(true, user);
        if (order != null)
            orderDetailService.remove(id);
        redirectAttributes.addFlashAttribute("msg1", "");
        return "redirect:/order/";
    }

    @PostMapping("/payment")
    public String Payment(@ModelAttribute OrderDetailDto orderDetailDto, Model model, RedirectAttributes redirectAttributes) {
        System.out.println(orderDetailDto.getOrderDetailList().size());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(authentication.getName());
        Order order = orderService.findCurrentOrder(true, user);
        order.setOrderDate(new Timestamp(new Date().getTime()));
        if (orderDetailDto != null && orderDetailDto.getOrderDetailList().size() != 0) {
            for (OrderDetail o : orderDetailDto.getOrderDetailList()) {
                if (o.getQuantityProduct() == null || o.getQuantityProduct() < 1)
                    return "redirect:/order/";
                orderDetailService.save(o);
            }
            model.addAttribute("now", LocalDateTime.now());
            model.addAttribute("order", order);
            return "oder/payment";
        }
        redirectAttributes.addFlashAttribute("msg", "Giỏ hàng rỗng");
        return "redirect:/order/";
    }

    @PostMapping("/confirm-payment")
    public String confirmPayment(@RequestParam String phoneNumber, @RequestParam int sale, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(authentication.getName());
        Order order = orderService.findCurrentOrder(true, user);
        Customer customer = customerService.findByCustomerPhoneNumber(phoneNumber);
        if (customer != null) {
            double totalPrice = 0;
            for (OrderDetail orderDetail : order.getOrderDetailSet()) {
                totalPrice += (orderDetail.getProductPrice() * orderDetail.getQuantityProduct());
            }
            if (totalPrice >= 500000)
                customer.setCustomerPoint(customer.getCustomerPoint() + 100);
            else if (totalPrice >= 200000)
                customer.setCustomerPoint(customer.getCustomerPoint() + 30);
            if (totalPrice > 100000)
                customer.setCustomerPoint(customer.getCustomerPoint() + 10);
            if (sale <= customer.getCustomerPoint()) {
                customer.setCustomerPoint(customer.getCustomerPoint() - sale);
                customerService.save(customer);
            } else {
                customerService.save(customer);
            }
        }
//        orderService.getPdf(order, sale);
        order.setOrderDate(new Timestamp(new Date().getTime()));
        order.setOrderStatus(false);
        orderService.save(order);
        redirectAttributes.addFlashAttribute("msg", "Đã thanh toán");
        return "redirect:/order/";
    }

    @GetMapping("/revenue")
    public String chart(Model model) {
        for (int i = 0; i < orderDetailService.revenue().size(); i++) {
            model.addAttribute("msg" + i, orderDetailService.revenue().get(i));
        }
        return "oder/chart";
    }
}
