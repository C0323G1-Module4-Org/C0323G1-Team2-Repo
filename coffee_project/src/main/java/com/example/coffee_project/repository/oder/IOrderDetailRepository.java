package com.example.coffee_project.repository.oder;

import com.example.coffee_project.model.oder.Order;
import com.example.coffee_project.model.oder.OrderDetail;
import com.example.coffee_project.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    OrderDetail findByOrderAndProduct(Order order, Product product);

    OrderDetail findByOrderDetailId(int orderDetailId);
}
