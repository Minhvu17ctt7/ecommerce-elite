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

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getListUserFirstPage() {
        List<ProductResponseDto> listProducts = getListProductPagination(1, 4, "email", "asc", null, false).getBody();
        return ResponseEntity.ok(listProducts);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getListProductPagination(@PathVariable int pageNumber, @RequestParam int pageSize,
                                                                             @RequestParam String sortField,
                                                                             @RequestParam String sortName, @RequestParam String keyword,
                                                                             @RequestParam(value = "deleted", required = false) Boolean deleted) {
        deleted = deleted == null ? false : deleted;
        List<ProductResponseDto> listProducts = productService.getAllCategoriesPagination(pageNumber, pageSize, sortField, sortName, keyword, deleted);
        return ResponseEntity.ok(listProducts);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Validated @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        ProductResponseDto productReponseDto = productService.createProduct(productCreateRequestDto);
        return ResponseEntity.ok(productReponseDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponseDto> updateProduct(@Validated @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        ProductResponseDto productResponseDto = productService.updateProduct(productUpdateRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
