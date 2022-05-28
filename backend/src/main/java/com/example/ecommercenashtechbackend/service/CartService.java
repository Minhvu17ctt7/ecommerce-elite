package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.CartItemRequestDto;
import com.example.ecommercenashtechbackend.dto.response.CartItemResponseDto;
import com.example.ecommercenashtechbackend.dto.response.CartResponseDto;

import java.util.List;

public interface CartService {
    CartResponseDto getCart(Long userId);

    List<CartItemResponseDto> deleteItemToCart(List<CartItemRequestDto> cartItemRequestDtoList, Long userId);

    List<CartItemResponseDto> addItemToCart(List<CartItemRequestDto> cartItemRequestDtoList, Long UserId);
}
