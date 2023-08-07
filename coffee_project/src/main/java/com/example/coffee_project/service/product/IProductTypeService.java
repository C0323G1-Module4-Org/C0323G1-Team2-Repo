package com.example.coffee_project.service.product;

import com.example.coffee_project.model.product.Product;
import com.example.coffee_project.model.product.ProductType;

import java.util.List;
import java.util.Set;

public interface IProductTypeService {
    List<ProductType>display();
    Set<Product> displayListProduct(int id);
}
