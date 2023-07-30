package com.example.coffee_project.service.customer;

import com.example.coffee_project.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {
    Page<Customer> findAll(Pageable pageable, String name);
    boolean save(Customer customer);
    Customer findById(int id);
    boolean update(int id, Customer customer);
    boolean deleteById(int id);
    Page<Customer> sortByName(Pageable pageable);
}
