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

    private ProductResponseDto RECORD_1;
    private ProductResponseDto RECORD_2;

    @BeforeEach
    public void setup() {
        RECORD_1 = ProductResponseDto.builder().id(1L).build();
        RECORD_2 = ProductResponseDto.builder().id(2L).build();
    }

    @WithUserDetails("admin")
    @Test
    public void getAllProduct_ShouldReturnListProductResponseDto_WhenGetSuccess() throws Exception {
        List<ProductResponseDto> resultExpected = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2));
        when(productService.getAllProducts(true)).thenReturn(resultExpected);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/products/all?deleted={deleted}", true)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(RECORD_1.getId()))
                .andExpect(jsonPath("$.data[1].id").value(RECORD_2.getId()));
    }
}
