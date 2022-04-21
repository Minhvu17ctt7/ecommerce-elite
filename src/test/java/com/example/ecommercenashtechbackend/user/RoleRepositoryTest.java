package com.example.ecommercenashtechbackend.user;

import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateAllRoles() {
        Role roleAdmin = Role.builder().name("ADMIN").description("Manage everything").build();
        Role roleUser = Role.builder().name("USER").description("Customer shopping").build();
        roleRepository.saveAll(List.of(roleAdmin, roleUser));
    }
}

