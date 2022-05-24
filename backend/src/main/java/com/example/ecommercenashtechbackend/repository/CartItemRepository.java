package com.example.ecommercenashtechbackend.repository;

import com.example.ecommercenashtechbackend.entity.CartItem;
import com.example.ecommercenashtechbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByProductIn(List<Product> products);
}
