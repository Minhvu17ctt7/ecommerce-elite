package com.example.ecommercenashtechbackend.repository;

import com.example.ecommercenashtechbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name = ?1 and p.deleted = false")
    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.id = ?1 and p.deleted = false")
    Optional<Product> findById(Long id);

    @Query("UPDATE Product p SET p.deleted = true WHERE p.id = ?1")
    @Modifying
    public void updateDeletedProductById(Long id);
}
