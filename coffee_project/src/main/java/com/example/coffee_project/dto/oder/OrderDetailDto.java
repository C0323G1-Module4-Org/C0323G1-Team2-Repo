package com.example.coffee_project.dto.oder;

import com.example.coffee_project.model.oder.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailDto {
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public double getTotalPrice() {
        double result = 0;
        for (OrderDetail o : orderDetailList) {
            result += o.getProductPrice() * o.getQuantityProduct();
        }
        return result;
    }

    @Override
    public String toString() {
        return "OrderDetailDto{" +
                "orderDetailList=" + orderDetailList +
                '}';
    }
}
