package com.example.coffee_project.repository.product;

import com.example.coffee_project.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select * from product as p where (p.product_name like concat('%',:name,'%') or :name='')and (p.product_type_id=:productType or :productType='') and (:minPrice ='' OR :maxPrice ='' OR p.product_price BETWEEN :minPrice AND :maxPrice)", nativeQuery = true)
    Page<Product> search(Pageable pageable, @Param("name") String name, @Param("productType") String productType, @Param("minPrice") String minPrice, @Param("maxPrice") String maxPrice);
}
