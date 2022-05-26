package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.OrderRequestDto;
import com.example.ecommercenashtechbackend.dto.response.OrderResponseDto;
import com.example.ecommercenashtechbackend.dto.response.PaginationResponseDto;

public interface OrderService {
    PaginationResponseDto<OrderResponseDto> getListOrderPagination(Long userId, int pageNumber, int pageSize, boolean deleted);

    OrderResponseDto getDetailOrder(Long userId, Long orderId, boolean deleted);

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long userId);
}
