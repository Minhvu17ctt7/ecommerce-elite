package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.ProductCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.request.ProductUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto);

    List<ProductResponseDto> getAllCategoriesPagination(int pageNumber, int pageSize, String sortField, String sortName, String keyword);

    ProductResponseDto updateProduct(ProductUpdateRequestDto productUpdateRequestDto);

    void deleteProduct(Long id);
}
