package com.example.coffee_project.repository.oder;

import com.example.coffee_project.model.oder.Order;
import com.example.coffee_project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderId(int orderId);

    Order findByOrderStatusAndAndUser(boolean status, User user);
}
