package com.example.ecommercenashtechbackend.repository;

import com.example.ecommercenashtechbackend.entity.Product;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findByName_ShouldReturnOptionalProduct_WhenFindByName() {
        Product product = Product.builder().name("Áo Thun Dry Cổ Tròn Ngắn Tay").build();
        productRepository.save(product);

        Optional<Product> productOptional = productRepository.findByName("Áo Thun Dry Cổ Tròn Ngắn Tay");
        assertThat(productOptional.isPresent()).isTrue();
        assertThat(productOptional.get().getName()).isEqualTo("Áo Thun Dry Cổ Tròn Ngắn Tay");
    }

    @Test
    public void findByName_ShouldReturnOptionalEmpty_WhenProductDeleted() {
        Product product = Product.builder().name("Áo Thun Dry Cổ Tròn Ngắn Tay").deleted(true).build();
        productRepository.save(product);

        Optional<Product> productOptional = productRepository.findByName("Áo Thun Dry Cổ Tròn Ngắn Tay");
        assertThat(productOptional.isEmpty()).isTrue();
    }

    @Test
    public void findByName_ShouldReturnOptionalEmpty_WhenProductNotFound() {
        Optional<Product> productOptional = productRepository.findByName("Áo Thun Dry Cổ Tròn Ngắn Tay");
        assertThat(productOptional.isEmpty()).isTrue();
    }

    @Test
    public void findById_ShouldReturnOptionalProduct_WhenFindById() {
        Optional<Product> productOptional = productRepository.findById(1L);
        assertThat(productOptional.isPresent()).isTrue();
        assertThat(productOptional.get().getId()).isEqualTo(1L);
    }


}
