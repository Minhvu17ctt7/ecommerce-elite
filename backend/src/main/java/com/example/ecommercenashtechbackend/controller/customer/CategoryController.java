package com.example.ecommercenashtechbackend.controller.customer;

import com.example.ecommercenashtechbackend.dto.response.CategoryResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ResponseDto;
import com.example.ecommercenashtechbackend.entity.Category;
import com.example.ecommercenashtechbackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllCategories() {
        List<CategoryResponseDto> listCategories = categoryService.getAllCategories();
        ResponseDto<List<CategoryResponseDto>> responseDto = new ResponseDto<>(200, listCategories, "Get category user successfully");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getCategoryDetail(@PathVariable Long id) {
        CategoryResponseDto category = categoryService.findCategoryById(id);
        ResponseDto<CategoryResponseDto> responseDto = new ResponseDto<>(200, category, "Get category successfully");
        return ResponseEntity.ok(responseDto);
    }

}
