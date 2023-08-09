package com.example.coffee_project.service.oder;

import com.example.coffee_project.model.oder.Order;
import com.example.coffee_project.model.oder.OrderDetail;
import com.example.coffee_project.model.product.Product;

import java.util.List;

public interface IOrderDetailService {
    void remove(int id);

    OrderDetail save(OrderDetail orderDetail);

    OrderDetail findByOrderAndProduct(Order order, Product product);

    OrderDetail findByOrderDetailId(int orderDetailId);
    List<String> revenue();

}
