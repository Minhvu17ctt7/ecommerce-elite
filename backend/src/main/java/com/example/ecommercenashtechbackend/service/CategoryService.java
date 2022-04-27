package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.CategoryRequestDto;
import com.example.ecommercenashtechbackend.dto.request.CategoryUpdateRequestDto;
import com.example.ecommercenashtechbackend.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category createCategory(CategoryRequestDto categoryRequestDto);

    List<Category> getAllCategoriesPagination(int pageNumber, int pageSize, String sortField, String sortName, String keyword);

    Category updateCategory(CategoryUpdateRequestDto categoryUpdateRequestDto);

    void deleteCategory(Long id);
}
