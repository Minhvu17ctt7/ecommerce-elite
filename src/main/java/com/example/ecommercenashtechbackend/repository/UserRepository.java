package com.example.ecommercenashtechbackend.repository;

import com.example.ecommercenashtechbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE CONCAT(u.email,' ',u.id,' ',u.firstName,' ',u.lastName) LIKE %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);
}
