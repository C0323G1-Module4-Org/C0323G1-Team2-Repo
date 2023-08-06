package com.example.coffee_project.model.oder;

import com.example.coffee_project.model.product.Product;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Integer orderDetailId;

    @Column(name = "quantity_product", nullable = false)
    private Integer quantityProduct;

    @Column(name = "product_price", nullable = false)
    private Double productPrice;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private Order order;

    public OrderDetail() {
    }

    public OrderDetail(Integer quantityProduct, Double productPrice, Product product, Order order) {
        this.quantityProduct = quantityProduct;
        this.productPrice = productPrice;
        this.product = product;
        this.order = order;
    }

    public OrderDetail(Integer orderDetailId, Integer quantityProduct,
                       Double productPrice, Product product, Order order) {
        this.orderDetailId = orderDetailId;
        this.quantityProduct = quantityProduct;
        this.productPrice = productPrice;
        this.product = product;
        this.order = order;
    }

    public Integer getOrderDetailId() {
        return this.orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getQuantityProduct() {
        return this.quantityProduct;
    }

    public void setQuantityProduct(Integer quantityProduct) {
        this.quantityProduct = quantityProduct;
    }

    public Double getProductPrice() {
        return this.productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailId=" + orderDetailId +
                ", quantityProduct=" + quantityProduct +
                ", productPrice=" + productPrice +
                ", product=" + product +
                ", order=" + order +
                '}';
    }
}
