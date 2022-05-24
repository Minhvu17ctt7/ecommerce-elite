package com.example.ecommercenashtechbackend.controller.customer;

import com.example.ecommercenashtechbackend.dto.request.NewCartItemRequestDto;
import com.example.ecommercenashtechbackend.dto.response.CartResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ResponseDto;
import com.example.ecommercenashtechbackend.security.UserDetail;
import com.example.ecommercenashtechbackend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity< ResponseDto<List<CartResponseDto>>> addNewItemToCart(@RequestBody List<NewCartItemRequestDto> newCartItemRequestDtoList) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail userDetail = (UserDetail)principal;

        List<CartResponseDto> cartResponseDto = cartService.addNewItemsToCart(newCartItemRequestDtoList, userDetail.getUser().getId());
        ResponseDto<List<CartResponseDto>> responseDto = new ResponseDto<>(200, cartResponseDto, "Add Item to cart successfully");
        return ResponseEntity.ok(responseDto);
    }

}
