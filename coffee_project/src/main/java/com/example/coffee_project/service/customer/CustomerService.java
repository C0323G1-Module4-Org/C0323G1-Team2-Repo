package com.example.coffee_project.service.customer;

import com.example.coffee_project.model.customer.Customer;
import com.example.coffee_project.repository.customer.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService{
    @Autowired
    private ICustomerRepository customerRepository;
    @Override
    public Page<Customer> findAll(Pageable pageable, String name) {
        return customerRepository.findCustomerByCustomerNameContaining(pageable,name);
    }

    @Override
    public boolean save(Customer customer) {
        Customer customer1 = customerRepository.save(customer);
        return customer1 != null;
    }

    @Override
    public Customer findById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public boolean update(int id, Customer customer) {
        if (findById(id) != null) {
            Customer customerUpdate = customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Page<Customer> sortByName(Pageable pageable) {
        return customerRepository.sortByCustomerName(pageable);
    }
}
