package com.example.ecommercenashtechbackend.repository;

import com.example.ecommercenashtechbackend.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Product PRODUCT_1;
    private Product PRODUCT_2;
    private Product PRODUCT_3;


    @Test
    public void findByName_ShouldReturnOptionalProduct_WhenFindByName() {
        Optional<Product> productOptional = productRepository.findByName("Áo Thun Dry Cổ Tròn Ngắn Tay");
        assertThat(productOptional.isPresent()).isTrue();
        assertThat(productOptional.get().getName()).isEqualTo("Áo Thun Dry Cổ Tròn Ngắn Tay");
    }

    @Test
    public void findById_ShouldReturnOptionalProduct_WhenFindById() {
        Optional<Product> productOptional = productRepository.findById(1L);
        assertThat(productOptional.isPresent()).isTrue();
        assertThat(productOptional.get().getId()).isEqualTo(1L);
    }

}
