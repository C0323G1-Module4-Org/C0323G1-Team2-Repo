package com.example.coffee_project.service.product;

import com.example.coffee_project.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IProductService {
    Page<Product> display(Pageable pageable);
    Page<Product> search(Pageable pageable,String name,String productType,String price);
    void add(Product product);
    void delete(int id);
    Product findProductById(int id);
    void edit(Product product);
}
