package com.example.coffee_project.service.oder;

import com.example.coffee_project.model.oder.Order;
import com.example.coffee_project.repository.oder.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {
    @Autowired
    IOrderRepository orderRepository;

    @Override
    public Order findByOrderId(int orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
