package com.example.ecommercenashtechbackend.repository;

import com.example.ecommercenashtechbackend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,  JpaSpecificationExecutor<Product> {
    @Query("SELECT p FROM Product p WHERE p.name = ?1 and p.deleted = false")
    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.id = ?1 and p.deleted = false")
    Optional<Product> findById(Long id);

    @Query("UPDATE Product p SET p.deleted = true WHERE p.id = ?1")
    @Modifying
    public void updateDeletedProductById(Long id);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% and p.deleted = ?2")
    public Page<Product> findAll(String keyword, boolean deleted, Pageable pageable);

    public Page<Product> findAll(Specification<Product> specification, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% and p.deleted = ?2 and p.category.id = ?3")
    public Page<Product> findAll(String keyword, boolean deleted,Long categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.deleted = ?2 and p.category.id = ?3")
    public Page<Product> findAll( boolean deleted,Long categoryId, Pageable pageable);
}
