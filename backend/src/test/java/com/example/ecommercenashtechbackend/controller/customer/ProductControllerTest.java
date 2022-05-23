package com.example.ecommercenashtechbackend.controller.customer;

import com.example.ecommercenashtechbackend.configuration.SpringSecurityWebAuxTestConfig;
import com.example.ecommercenashtechbackend.dto.response.ProductPaginationResponseDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.webjars.NotFoundException;

import java.util.List;

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

    ProductResponseDto RECORD_1;
    ProductResponseDto RECORD_2;

    @BeforeEach
    public void setup() {
        RECORD_1 = ProductResponseDto.builder().id(1L).build();
        RECORD_2 = ProductResponseDto.builder().id(2L).build();
    }

    @Test
    public void getProductDetail_ShouldReturnProductResponseDto_WhenGetSuccess() throws Exception {
        when(productService.getProductDetail(RECORD_1.getId())).thenReturn(RECORD_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/{id}", RECORD_1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(RECORD_1.getId()));
    }

    @Test
    public void getProductDetail_ShouldReturnProductNotFound_WhenNotFoundProductById() throws Exception {
        Long id = 2L;
        when(productService.getProductDetail(id)).thenThrow(new NotFoundException("Not found product with id: " + id));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found product with id: " + id ));
    }

    @Test
    public void getListProductPaginationBySpecification_shouldReturnListProduct_WhenGetSuccess() throws Exception {
        int pageNumber = 1;
        int pageSize = 8;
        String sortField = "name";
        String sortName = "asc";
        String search = "";
        ProductPaginationResponseDto productPaginationResponseDto = ProductPaginationResponseDto.builder().products(List.of(RECORD_1, RECORD_2)).sizePage(8).totalPage(2).build();


        when(productService.getAllProductsPaginationBySpecification(pageNumber, pageSize, sortField, sortName, search, false)).thenReturn(
                productPaginationResponseDto
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/page/{pageNumber}?pageSize={pageSize}&sortField={sortField}&sortName={sortName}&" +
                                "search={search}", pageNumber, pageSize, sortField, sortName, search))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.sizePage").value(pageSize))
                .andExpect(jsonPath("$.data.totalPage").value(2))
                ;
    }

}
