package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.CartItemRequestDto;
import com.example.ecommercenashtechbackend.dto.response.CartItemResponseDto;

import java.util.List;

public interface CartService {
    List<CartItemResponseDto> deleteItemToCart(List<CartItemRequestDto> cartItemRequestDtoList, Long userId);

    List<CartItemResponseDto> changeCartItemsInCart(List<CartItemRequestDto> cartItemRequestDtoList, Long UserId);
}
