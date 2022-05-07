package com.example.ecommercenashtechbackend.repository;

import com.example.ecommercenashtechbackend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT p FROM Review p WHERE p.product.id = ?1")
    public Page<Review> findAllByProductId(Pageable pageable, Long productId);
}
