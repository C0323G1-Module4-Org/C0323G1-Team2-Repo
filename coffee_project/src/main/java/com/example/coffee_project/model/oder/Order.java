package com.example.coffee_project.model.oder;

import com.example.coffee_project.model.customer.Customer;
import com.example.coffee_project.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_status")
    private Boolean orderStatus;

    @Column(name = "order_date", nullable = false)
    private Timestamp orderDate;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @JsonBackReference
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetailSet;

    public Order(Integer orderId, Boolean orderStatus, Timestamp orderDate, Customer customer, User user,
                 Set<OrderDetail> orderDetailSet) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.customer = customer;
        this.user = user;
        this.orderDetailSet = orderDetailSet;
    }

    public Order(Boolean orderStatus, Timestamp orderDate, User user) {
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.user = user;
    }

    public Set<OrderDetail> getOrderDetailSet() {
        return orderDetailSet;
    }

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet) {
        this.orderDetailSet = orderDetailSet;
    }

    public Order() {
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Boolean getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalPrice() {
        double result = 0;
        for (OrderDetail od : orderDetailSet) {
            result += od.getProductPrice() * od.getQuantityProduct();
        }
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderStatus=" + orderStatus +
                ", orderDate=" + orderDate +
                ", customer=" + customer +
                ", user=" + user +
                ", orderDetailSet=" + orderDetailSet +
                '}';
    }
}
