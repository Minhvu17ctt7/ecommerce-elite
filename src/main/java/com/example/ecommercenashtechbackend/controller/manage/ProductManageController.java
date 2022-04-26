package com.example.ecommercenashtechbackend.controller.manage;

import com.example.ecommercenashtechbackend.dto.request.ProductCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.request.ProductUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductManageController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Validated @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        ProductResponseDto productReponseDto = productService.createProduct(productCreateRequestDto);
        return ResponseEntity.ok(productReponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProductPagination(@PathVariable int pageNumber, @RequestParam int pageSize,
                                                                         @RequestParam String sortField,
                                                                         @RequestParam String sortName, @RequestParam String keyword) {
        List<ProductResponseDto> listCategories = productService.getAllCategoriesPagination(pageNumber, pageSize, sortField, sortName, keyword);
        return ResponseEntity.ok(listCategories);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponseDto> updateProduct(@Validated @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        ProductResponseDto productResponseDto = productService.updateProduct(productUpdateRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }
}
