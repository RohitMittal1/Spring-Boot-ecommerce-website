package com.eRohit.Ecom_proj.repo;

import com.eRohit.Ecom_proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(String keyword);

    Optional<Product> findByNameIgnoreCase(String name);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) = LOWER(:name) AND LOWER(p.brand) = LOWER(:brand)")
    Optional<Product> findByNameAndBrand(String name, String brand);
}
