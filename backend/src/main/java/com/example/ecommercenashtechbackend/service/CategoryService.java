package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.CategoryRequestDto;
import com.example.ecommercenashtechbackend.dto.request.CategoryUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.CategoryResponseDto;
import com.example.ecommercenashtechbackend.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> getAllCategories(boolean deleted);

    CategoryResponseDto findCategoryById(Long id);

    List<Category> getAllCategoriesPagination(int pageNumber, int pageSize, String sortField, String sortName, String keyword);

    CategoryResponseDto updateCategory(CategoryUpdateRequestDto categoryUpdateRequestDto);

    void deleteCategory(Long id);

    boolean checkAvailableDelete(Long categoryId);
}
