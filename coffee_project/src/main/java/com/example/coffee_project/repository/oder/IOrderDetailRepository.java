package com.example.coffee_project.repository.oder;

import com.example.coffee_project.model.oder.Order;
import com.example.coffee_project.model.oder.OrderDetail;
import com.example.coffee_project.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    OrderDetail findByOrderAndProduct(Order order, Product product);

    OrderDetail findByOrderDetailId(int orderDetailId);

    @Query(value = "SELECT sum(coffee.d.product_price * coffee.d.quantity_product) as danh_thu FROM coffee.order_detail d join coffee.orders o \n" +
            "on d.order_id=o.order_id\n" +
            "where MONTH(o.order_date)=MONTH(CURDATE())-3 and o.order_status=false", nativeQuery = true)
    String revenue3();

    @Query(value = "SELECT sum(coffee.d.product_price * coffee.d.quantity_product) as danh_thu FROM coffee.order_detail d join coffee.orders o \n" +
            "on d.order_id=o.order_id\n" +
            "where MONTH(o.order_date)=MONTH(CURDATE())-2 and o.order_status=false", nativeQuery = true)
    String revenue2();

    @Query(value = "SELECT sum(coffee.d.product_price * coffee.d.quantity_product) as danh_thu FROM coffee.order_detail d join coffee.orders o \n" +
            "on d.order_id=o.order_id\n" +
            "where MONTH(o.order_date)=MONTH(CURDATE())-1 and o.order_status=false", nativeQuery = true)
    String revenue1();
}
