package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.NewCartItemRequestDto;
import com.example.ecommercenashtechbackend.dto.response.CartResponseDto;

import java.util.List;

public interface CartService {
    List<CartResponseDto> addNewItemsToCart(List<NewCartItemRequestDto> newCartItemRequestDtoList, Long UserId);
}
