package com.example.ecommercenashtechbackend.controller.manage;

import com.example.ecommercenashtechbackend.dto.request.ProductCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.request.ProductUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ResponseDto;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
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
    public ResponseEntity<ResponseDto> getListUserFirstPage() {
        ResponseDto<List<ProductResponseDto>> responseDto = getListProductPagination(1, 4, "email", "asc", null, false).getBody();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/page")
    public ResponseEntity<ResponseDto> getListProductPagination(@PathVariable("page") int pageNumber, @RequestParam int pageSize,
                                                                             @RequestParam String sortField,
                                                                             @RequestParam String sortName, @RequestParam String keyword,
                                                                             @RequestParam(value = "deleted", required = false) Boolean deleted) {
        deleted = deleted == null ? false : deleted;
        List<ProductResponseDto> listProducts = productService.getAllCategoriesPagination(pageNumber, pageSize, sortField, sortName, keyword, deleted);
        ResponseDto<List<ProductResponseDto>> responseDto = new ResponseDto<>(200, listProducts, "Get list user successfully");
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createProduct(@Validated @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        ProductResponseDto productReponseDto = productService.createProduct(productCreateRequestDto);
        ResponseDto<ProductResponseDto> responseDto = new ResponseDto<>(200, productReponseDto, "Create user successfully");
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateProduct(@Validated @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        ProductResponseDto productResponseDto = productService.updateProduct(productUpdateRequestDto);
        ResponseDto<ProductResponseDto> responseDto = new ResponseDto<>(200, productResponseDto, "Update user successfully");
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        ResponseDto<Long> responseDto = new ResponseDto<>(200, id, "Delete user "+id+" successfully");
        return ResponseEntity.ok(responseDto);
    }
}
