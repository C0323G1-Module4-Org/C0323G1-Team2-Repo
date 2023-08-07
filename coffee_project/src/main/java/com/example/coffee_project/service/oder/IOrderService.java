package com.example.coffee_project.service.oder;

import com.example.coffee_project.model.oder.Order;
import com.example.coffee_project.model.user.User;

public interface IOrderService {
    Order findByOrderId(int orderId);

    Order save(Order order);

    Order findCurrentOrder(boolean status, User user);
}
