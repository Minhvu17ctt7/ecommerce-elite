package com.example.ecommercenashtechbackend.controller.customer;

import com.example.ecommercenashtechbackend.configuration.SpringSecurityWebAuxTestConfig;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProductDetail_ShouldReturnProductResponseDto_WhenGetSuccess() throws Exception {
        ProductResponseDto productResponseDto = ProductResponseDto.builder().id(1L).build();
        when(productService.getProductDetail(1L)).thenReturn(productResponseDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/{id}", productResponseDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productResponseDto.getId()));
    }
}
