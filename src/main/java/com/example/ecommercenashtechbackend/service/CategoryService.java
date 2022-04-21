package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.CategoryRequestDto;
import com.example.ecommercenashtechbackend.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category saveCategory(CategoryRequestDto categoryRequestDto);
}
