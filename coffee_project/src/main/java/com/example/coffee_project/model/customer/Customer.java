package com.example.coffee_project.model.customer;

import com.example.coffee_project.model.oder.Order;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_gender")
    private Boolean customerGender;

    @Column(name = "customer_birthday")
    private Date customerBirthday;

    @Column(name = "customer_phone_number", nullable = false,unique = true)
    private String customerPhoneNumber;

    @Column(name = "customer_point")
    private Integer customerPoint;
    @OneToMany(mappedBy = "customer")
    private Set<Order> orderSet;

    public Customer(Integer customerId, String customerName, Boolean customerGender, Date customerBirthday,
                    String customerPhoneNumber, Integer customerPoint, Set<Order> orderSet) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerGender = customerGender;
        this.customerBirthday = customerBirthday;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerPoint = customerPoint;
        this.orderSet = orderSet;
    }

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public Customer() {
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean getCustomerGender() {
        return this.customerGender;
    }

    public void setCustomerGender(Boolean customerGender) {
        this.customerGender = customerGender;
    }

    public Date getCustomerBirthday() {
        return this.customerBirthday;
    }

    public void setCustomerBirthday(Date customerBirthday) {
        this.customerBirthday = customerBirthday;
    }

    public String getCustomerPhoneNumber() {
        return this.customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public Integer getCustomerPoint() {
        return this.customerPoint;
    }

    public void setCustomerPoint(Integer customerPoint) {
        this.customerPoint = customerPoint;
    }
}
