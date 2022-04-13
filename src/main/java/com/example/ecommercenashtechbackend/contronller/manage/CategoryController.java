package com.example.ecommercenashtechbackend.contronller.manage;

import com.example.ecommercenashtechbackend.entity.Category;
import com.example.ecommercenashtechbackend.dto.request.CategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> listCategories = new ArrayList<>();
        return ResponseEntity.ok(listCategories);
    }

    @PostMapping("/categories/new")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = Category.builder().name("Laptop").alias("laptop").description("Ecommerce laptop").build();
        return ResponseEntity.ok(category);
    }
}
