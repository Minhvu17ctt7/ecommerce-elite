package com.example.ecommercenashtechbackend.controller.manage;

import com.example.ecommercenashtechbackend.configuration.SpringSecurityWebAuxTestConfig;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
public class ProductManageControllerTest {

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private MockMvc mockMvc;
    private ProductResponseDto record_1;
    private ProductResponseDto record_2;

    @BeforeEach
    public void setup() {
        record_1 = mock(ProductResponseDto.class);
        record_2 = mock(ProductResponseDto.class);
    }

    @WithUserDetails("user@gmail.com")
    @Test
    public void getAllProduct_ShouldReturnListProductResponseDto_WhenGetSuccess() throws Exception {
        List<ProductResponseDto> resultExpected = new ArrayList<>(Arrays.asList(record_1, record_2));
        when(productService.getAllProducts(anyBoolean())).thenReturn(resultExpected);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/admin/products?deleted=true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
