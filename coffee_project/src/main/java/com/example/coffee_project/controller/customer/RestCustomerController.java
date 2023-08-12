package com.example.coffee_project.controller.customer;

import com.example.coffee_project.model.customer.Customer;
import com.example.coffee_project.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/customer")
public class RestCustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/view/{id}")
    public ResponseEntity<Customer> showDetail(@PathVariable Integer id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/config-phone-number/{phoneNumber}")
    public ResponseEntity<Customer> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        Customer customer = customerService.findByCustomerPhoneNumber(phoneNumber);
        if (customer != null)
            return new ResponseEntity<>(customer, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
