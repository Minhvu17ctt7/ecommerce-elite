package com.example.ecommercenashtechbackend.user;

import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RoleRepositoryTest {

    private RoleRepository roleRepository;

    @Autowired
    public RoleRepositoryTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    void when_CreateRoleUser_Expect_roleIsSaved() {
        Role role = Role.builder().name("USER").description("Customer").build();
        Role savedRole = roleRepository.save(role);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    void when_CreateRoleAdmin_Expect_roleIsSaved() {
        Role role = Role.builder().name("ADMIN").description("Manage everything").build();
        Role savedRole = roleRepository.save(role);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

}
