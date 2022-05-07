package com.example.ecommercenashtechbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPaginationResponse {
    private ProductResponseDto productResponseDto;
    private int totalPage;
    private int currentPage;
    private int sizePage;
}
