package com.example.ecommercenashtechbackend.controller.customer;

import com.example.ecommercenashtechbackend.dto.response.ProductPaginationResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ResponseDto;
import com.example.ecommercenashtechbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/page/{page}")
    public ResponseEntity<ResponseDto<ProductPaginationResponseDto>> getListProductPaginationBySpecification(@PathVariable("page") int pageNumber, @RequestParam(required = false) int pageSize,
                                                                @RequestParam(required = false) String sortField,
                                                                @RequestParam(required = false) String sortName, @RequestParam(required = false) String search
                                                               ) {
        ProductPaginationResponseDto listProducts = productService.getAllProductsPaginationBySpecification(pageNumber, pageSize, sortField, sortName, search, false);
        ResponseDto<ProductPaginationResponseDto> responseDto = new ResponseDto<>(200, listProducts, "Get list product successfully");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductResponseDto>> getProductDetail(@PathVariable Long id) {
        ProductResponseDto productResponseDto = productService.getProductDetail(id);
        ResponseDto<ProductResponseDto> responseDto = new ResponseDto<>(200, productResponseDto, "Get product successfully");
        return ResponseEntity.ok(responseDto);
    }

}
