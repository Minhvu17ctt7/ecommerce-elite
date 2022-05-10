package com.example.ecommercenashtechbackend.repository;

import com.example.ecommercenashtechbackend.entity.Category;
import com.example.ecommercenashtechbackend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndDeleted(Long id, boolean deleted);

    Optional<Category> findByName(String name);

    Page<Category> findByNameContaining(String name, Pageable pageable);

    List<Category> findAllByDeleted(boolean deleted);

    @Query("UPDATE Category p SET p.deleted = true WHERE p.id = ?1")
    @Modifying
    public void updateDeletedProductById(Long id);
}
