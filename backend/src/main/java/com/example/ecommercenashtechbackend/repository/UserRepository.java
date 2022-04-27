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
    @Query("SELECT u FROM User u WHERE u.id = ?1 and u.deleted = false")
    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u WHERE u.email = ?1 and u.deleted = false")
    Optional<User> findByEmail(String email);

    @Query("UPDATE User u SET u.deleted = true WHERE u.id = ?1")
    @Modifying
    public void updateDeletedUserById(Long id);

    @Query("SELECT u FROM User u WHERE CONCAT(u.email,' ',u.id,' ',u.firstName,' ',u.lastName) LIKE %?1% and u.deleted = ?2")
    public Page<User> findAll(String keyword, boolean deleted, Pageable pageable);
}