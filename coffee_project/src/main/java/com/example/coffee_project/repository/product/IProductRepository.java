package com.example.coffee_project.repository.product;

import com.example.coffee_project.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select * from product as p where (p.product_name like concat('%',:name,'%') or :name='')and (p.product_type_id=:productType or :productType='') and (:minPrice ='' OR :maxPrice ='' OR p.product_price BETWEEN :minPrice AND :maxPrice)", nativeQuery = true)
    Page<Product> search(Pageable pageable, @Param("name") String name, @Param("productType") String productType, @Param("minPrice") String minPrice, @Param("maxPrice") String maxPrice);
    Page<Product> findAllByProductNameContaining(Pageable pageable, String name);
    @Modifying
    @Transactional
    @Query(value = "SET FOREIGN_KEY_CHECKS=0", nativeQuery = true)
    void disableForeignKeyChecks();
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `coffee`.`product` WHERE (`product_id` = :id)",nativeQuery = true)
    void delete(@Param("id")int id);
    @Modifying
    @Transactional
    @Query(value = "SET FOREIGN_KEY_CHECKS=1", nativeQuery = true)
    void enableForeignKeyChecks();
    @Query(value = "select p.product_id,p.product_name,p.product_price,p.product_description,p.product_image_path,p.product_type_id,sum(od.quantity_product) as quantity from product p left join order_detail od on od.product_id=p.product_id group by p.product_id order by quantity desc limit 4;",nativeQuery = true)
    List<Product> getBestSeller();
}
