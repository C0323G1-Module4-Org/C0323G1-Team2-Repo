package com.example.coffee_project.service.product;

import com.example.coffee_project.model.product.Product;
import com.example.coffee_project.repository.product.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Page<Product> searchByName(Pageable pageable, String name) {
        return productRepository.findAllByProductNameContaining(pageable, name);
    }

    @Override
    public Page<Product> display(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> search(Pageable pageable, String name, String productType, String price) {
        String minPrice = "";
        String maxPrice = "";
        if (!price.isEmpty()) {
            String[] priceRange = price.split("-");
            if (priceRange.length == 2) {
                minPrice = new String(priceRange[0]);
                maxPrice = new String(priceRange[1]);
            }
        }
        String newStr = minPrice.replaceAll(",", "");
        String newStr2 = maxPrice.replaceAll(",", "");
        return productRepository.search(pageable, name, productType, newStr, newStr2);
    }

    @Override
    public void add(Product product) {
        productRepository.save(product);
    }

    @Override
    public void delete(int id) {
        productRepository.disableForeignKeyChecks();
        productRepository.delete(id);
        productRepository.enableForeignKeyChecks();
    }

    @Override
    public Product findProductById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void edit(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getBestSeller() {
        return productRepository.getBestSeller();
    }
    @Override
    public Page<Product> searchByNameAndProductType(Pageable pageable, String name, String type) {
        return productRepository.findByNameAndType(pageable, name, type);
    }
    @Override
    public boolean isExitProduct(int id) {
        return productRepository.existsById(id);
    }
}
