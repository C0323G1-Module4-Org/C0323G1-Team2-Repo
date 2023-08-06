package com.example.coffee_project.service.oder;

import com.example.coffee_project.model.oder.Order;
import com.example.coffee_project.model.oder.OrderDetail;
import com.example.coffee_project.model.product.Product;
import com.example.coffee_project.repository.oder.IOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    IOrderDetailRepository orderDetailRepository;

    @Override
    public void remove(int id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }


    @Override
    public OrderDetail findByOrderAndProduct(Order order, Product product) {
        return orderDetailRepository.findByOrderAndProduct(order, product);
    }

    @Override
    public OrderDetail findByOrderDetailId(int orderDetailId) {
        return orderDetailRepository.findByOrderDetailId(orderDetailId);
    }

}
