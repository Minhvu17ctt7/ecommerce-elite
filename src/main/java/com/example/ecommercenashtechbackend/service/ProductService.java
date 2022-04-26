package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.ProductCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductCreateResponseDto;

public interface ProductService {
    ProductCreateResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto);
}
