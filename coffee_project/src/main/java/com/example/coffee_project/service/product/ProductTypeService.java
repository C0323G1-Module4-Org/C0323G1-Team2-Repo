package com.example.coffee_project.service.product;

import com.example.coffee_project.model.product.Product;
import com.example.coffee_project.model.product.ProductType;
import com.example.coffee_project.repository.product.IProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductTypeService implements IProductTypeService{
    @Autowired
    private IProductTypeRepository productTypeRepository;
    @Override
    public List<ProductType> display() {
        return productTypeRepository.findAll();
    }

    @Override
    public Set<Product> displayListProduct(int id) {
        ProductType productType=productTypeRepository.findById(id).get();
        Set<Product>productSet=productType.getProductSet();
        return productSet;
    }
}
