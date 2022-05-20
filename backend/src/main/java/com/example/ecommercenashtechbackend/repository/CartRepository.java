package com.example.ecommercenashtechbackend.repository;

import com.example.ecommercenashtechbackend.entity.Cart;
import com.example.ecommercenashtechbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByUser(User user);
}
