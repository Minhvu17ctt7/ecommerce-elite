package com.example.ecommercenashtechbackend.controller.customer;

import com.example.ecommercenashtechbackend.dto.request.CartItemRequestDto;
import com.example.ecommercenashtechbackend.dto.request.ValidList;
import com.example.ecommercenashtechbackend.dto.response.CartItemResponseDto;
import com.example.ecommercenashtechbackend.dto.response.CartResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ResponseDto;
import com.example.ecommercenashtechbackend.security.UserDetail;
import com.example.ecommercenashtechbackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity< ResponseDto<List<CartItemResponseDto>>> addNewItemToCart(@Validated @RequestBody ValidList<CartItemRequestDto> cartItemRequestDtoList) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail userDetail = (UserDetail)principal;

        List<CartItemResponseDto> cartItemResponseDto = cartService.addItemToCart(cartItemRequestDtoList.getList(), userDetail.getUser().getId());
        ResponseDto<List<CartItemResponseDto>> responseDto = new ResponseDto<>(200, cartItemResponseDto, "Add Item to cart successfully");
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/delete")
    public ResponseEntity< ResponseDto<List<CartItemResponseDto>>> deleteItemToCart(@Validated @RequestBody ValidList<CartItemRequestDto> cartItemRequestDtoList) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail userDetail = (UserDetail)principal;

        List<CartItemResponseDto> cartItemResponseDto = cartService.deleteItemToCart(cartItemRequestDtoList.getList(), userDetail.getUser().getId());
        ResponseDto<List<CartItemResponseDto>> responseDto = new ResponseDto<>(200, cartItemResponseDto, "Remove Item to cart successfully");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity< ResponseDto<CartResponseDto>> getCart() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail userDetail = (UserDetail)principal;

        CartResponseDto cartResponseDto = cartService.getCart(userDetail.getUser().getId());
        ResponseDto<CartResponseDto> responseDto = new ResponseDto<>(200, cartResponseDto, "Get cart successfully");
        return ResponseEntity.ok(responseDto);
    }

}
