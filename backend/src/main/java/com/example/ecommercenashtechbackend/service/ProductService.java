package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.ProductCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.request.ProductUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductPaginationResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto getProductDetail(Long id);

    List<ProductResponseDto> getAllProducts(boolean deleted);

    ProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto);

    ProductPaginationResponseDto getAllProductsPagination(int pageNumber, int pageSize, String sortField, String sortName, String keywork, boolean deleted);

    ProductPaginationResponseDto getAllProductsPaginationBySpecification(int pageNumber, int pageSize, String sortField, String sortName, String keywork, boolean deleted);

    ProductResponseDto updateProduct(ProductUpdateRequestDto productUpdateRequestDto);

    void deleteProduct(Long id);
}
