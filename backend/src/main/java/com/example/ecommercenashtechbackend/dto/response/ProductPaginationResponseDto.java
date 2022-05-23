package com.example.ecommercenashtechbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPaginationResponseDto {
    private List<ProductResponseDto> products;
    private int totalPage;
    private int sizePage;
}
