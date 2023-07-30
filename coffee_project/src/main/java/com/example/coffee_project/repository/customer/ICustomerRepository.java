package com.example.coffee_project.repository.customer;

import com.example.coffee_project.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
    Page<Customer> findCustomerByCustomerNameContaining(Pageable pageable,String name);
    @Query(value = "select * from customer order by customer_name",nativeQuery = true)
    Page<Customer> sortByCustomerName(Pageable pageable);
}
