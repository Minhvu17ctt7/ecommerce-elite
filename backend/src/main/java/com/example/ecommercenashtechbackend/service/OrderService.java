package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.OrderRequestDto;
import com.example.ecommercenashtechbackend.dto.response.OrderResponseDto;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long userId);
}
