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

//    @GetMapping
//    public ResponseEntity<ResponseDto> getListUserFirstPage() {
//        ResponseDto<ProductPaginationResponseDto> responseDto = getListProductPagination(1, 4, "name", "asc", null).getBody();
//        return ResponseEntity.ok(responseDto);
//    }

//    @GetMapping("/page/{page}")
//    public ResponseEntity<ResponseDto> getListProductPagination(@PathVariable("page") int pageNumber, @RequestParam(required = false) int pageSize,
//                                                                @RequestParam(required = false) String sortField,
//                                                                @RequestParam(required = false) String sortName, @RequestParam(required = false) String keyword
//                                                               ) {
//        ProductPaginationResponseDto listProducts = productService.getAllCategoriesPagination(pageNumber, pageSize, sortField, sortName, keyword, false);
//        ResponseDto<ProductPaginationResponseDto> responseDto = new ResponseDto<>(200, listProducts, "Get list product successfully");
//        return ResponseEntity.ok(responseDto);
//    }

    @GetMapping("/page/{page}")
    public ResponseEntity<ResponseDto> getListProductPaginationBySpecification(@PathVariable("page") int pageNumber, @RequestParam(required = false) int pageSize,
                                                                @RequestParam(required = false) String sortField,
                                                                @RequestParam(required = false) String sortName, @RequestParam(required = false) String search
                                                               ) {
        ProductPaginationResponseDto listProducts = productService.getAllCategoriesPaginationBySpecification(pageNumber, pageSize, sortField, sortName, search, false);
        ResponseDto<ProductPaginationResponseDto> responseDto = new ResponseDto<>(200, listProducts, "Get list product successfully");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getProductDetail(@PathVariable Long id) {
        ProductResponseDto productResponseDto = productService.getProductDetail(id);
        ResponseDto<ProductResponseDto> responseDto = new ResponseDto<>(200, productResponseDto, "Get product successfully");
        return ResponseEntity.ok(responseDto);
    }

}
