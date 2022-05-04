package com.example.ecommercenashtechbackend.controller.customer;

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

//    @GetMapping
//    public ResponseEntity<List<Category>> getAllCategories() {
//        return categoryService.getAllCategories();
//    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<List<Category>> getAllCategoriesPagination(@PathVariable int pageNumber, @RequestParam int pageSize,
                                                                     @RequestParam String sortField,
                                                                     @RequestParam String sortName, @RequestParam String keyword) {
        List<Category> listCategories = categoryService.getAllCategoriesPagination(pageNumber, pageSize, sortField, sortName, keyword);
        return ResponseEntity.ok(listCategories);
    }

}
