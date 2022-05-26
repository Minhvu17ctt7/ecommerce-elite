package com.example.ecommercenashtechbackend.repository;

import com.example.ecommercenashtechbackend.entity.Order;
import com.example.ecommercenashtechbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUserAndDeleted(User user, boolean deleted, Pageable pageable);
    Optional<Order> findOrderByIdAndUserAndDeleted(Long id, User user, boolean deleted);
}
