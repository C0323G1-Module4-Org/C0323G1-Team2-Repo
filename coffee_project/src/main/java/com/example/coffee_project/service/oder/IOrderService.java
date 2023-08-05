package com.example.coffee_project.service.oder;

import com.example.coffee_project.model.oder.Order;

public interface IOrderService {
    Order findByOrderId(int orderId);
    Order save(Order order);
}
