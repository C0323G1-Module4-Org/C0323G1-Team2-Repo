package com.example.coffee_project.model.product;

import com.example.coffee_project.model.oder.OrderDetail;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Double productPrice;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_image_path",columnDefinition = "MEDIUMTEXT")
    private String productImagePath;
    @ManyToOne
    @JoinColumn(name = "product_type_id", referencedColumnName = "product_type_id",nullable = false)
    private ProductType productType;
    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetailSet;
    public Product() {
    }
    public Product(Integer productId, String productName, Double productPrice, String productDescription,
                   String productImagePath, ProductType productType, Set<OrderDetail> orderDetailSet) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImagePath = productImagePath;
        this.productType = productType;
        this.orderDetailSet = orderDetailSet;
    }

    public Set<OrderDetail> getOrderDetailSet() {
        return orderDetailSet;
    }

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet) {
        this.orderDetailSet = orderDetailSet;
    }



    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return this.productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return this.productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImagePath() {
        return this.productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
