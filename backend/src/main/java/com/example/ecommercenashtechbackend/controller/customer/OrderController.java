package com.example.ecommercenashtechbackend.controller.customer;

import com.example.ecommercenashtechbackend.dto.request.OrderRequestDto;
import com.example.ecommercenashtechbackend.dto.response.OrderResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ResponseDto;
import com.example.ecommercenashtechbackend.security.UserDetail;
import com.example.ecommercenashtechbackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity< ResponseDto<OrderResponseDto>> createOrder(@Validated @RequestBody OrderRequestDto orderRequestDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail userDetail = (UserDetail)principal;

        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto, userDetail.getUser().getId());
        ResponseDto<OrderResponseDto> responseDto = new ResponseDto<>(200, orderResponseDto, "Create order successfully");
        return ResponseEntity.ok(responseDto);
    }
}
