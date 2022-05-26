package com.example.ecommercenashtechbackend.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPaginationResponseDto {
    private List<ProductResponseDto> products;
    private int totalPage;
    private int sizePage;
}
