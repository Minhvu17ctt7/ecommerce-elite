package com.example.ecommercenashtechbackend.controller.manage;

import com.example.ecommercenashtechbackend.dto.request.ProductCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductCreateResponseDto;
import com.example.ecommercenashtechbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductManageController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Validated @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        ProductCreateResponseDto productCreateReponseDto = productService.createProduct(productCreateRequestDto);
        return ResponseEntity.ok(productCreateReponseDto);
    }
}
