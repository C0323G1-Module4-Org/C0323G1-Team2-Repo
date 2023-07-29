package com.example.coffee_project.dto.product;

import com.example.coffee_project.model.product.ProductType;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductDto implements Validator {
    private Integer productId;
    private String productDescription;
    private String productImagePath;
    @NotBlank(message = "không được để trống")
    private String productName;
    @Min(value = 1,message = "giá phải là số dương ")
    private Double productPrice;
    private ProductType productType;

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ProductDto(Integer productId, String productDescription, String productImagePath, String productName, Double productPrice, ProductType productType) {
        this.productId = productId;
        this.productDescription = productDescription;
        this.productImagePath = productImagePath;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;
    }

    public ProductDto() {
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
