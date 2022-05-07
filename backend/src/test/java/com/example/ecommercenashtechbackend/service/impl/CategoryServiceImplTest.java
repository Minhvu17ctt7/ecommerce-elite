package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.CategoryRequestDto;
import com.example.ecommercenashtechbackend.dto.request.CategoryUpdateRequestDto;
import com.example.ecommercenashtechbackend.entity.Category;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.repository.CategoryRepository;
import com.example.ecommercenashtechbackend.service.CategoryService;
import com.example.ecommercenashtechbackend.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.webjars.NotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {

    private CategoryService categoryService;
    private Category categoryInital;
    private Category categoryParent;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private Util util;
    private CategoryUpdateRequestDto categoryUpdateRequestDto;
    private CategoryRequestDto categoryRequestDto;

    @BeforeEach
    public void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        util = Mockito.mock(Util.class);
        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper, util);
        categoryInital = Mockito.mock(Category.class);
        categoryParent = Mockito.mock(Category.class);
        categoryUpdateRequestDto = Mockito.mock(CategoryUpdateRequestDto.class);
        categoryRequestDto = Mockito.mock(CategoryRequestDto.class);
    }

    @Test
    public void createCategory_ShouldThrowConflictException_WhenCategoryNameAlreadyExists() {
        //TODO
        when(categoryRepository.findByName(categoryInital.getName())).thenReturn(java.util.Optional.ofNullable(categoryInital));
        ConflictException conflictException = assertThrows(ConflictException.class, () -> categoryService.createCategory(categoryRequestDto));
        assertThat(conflictException.getMessage()).isEqualTo("Category already exists");
    }

    @Test
    public void createCategory_shouldThrowNotFoundException_WhenCategoryParentNotFound() {
        //TODO
        when(categoryRepository.findByName(categoryRequestDto.getName())).thenReturn(java.util.Optional.ofNullable(null));
        when(categoryRepository.findById(categoryRequestDto.getParentId())).thenReturn(java.util.Optional.ofNullable(null));
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> categoryService.createCategory(categoryRequestDto));
        assertThat(notFoundException.getMessage()).isEqualTo("Parent category not found");
    }

    @Test
    public void saveCategory_ShouldReturnCategoryHasParent_WhenCategoryParentExist() {
        categoryRequestDto.setParentId(2L);
        when(categoryRepository.findByName(categoryInital.getName())).thenReturn(java.util.Optional.ofNullable(null));
        when(categoryRepository.findById(2L)).thenReturn(java.util.Optional.of(categoryParent));
        categoryInital.setParent(categoryParent);
        when(categoryRepository.save(any(Category.class))).thenReturn(categoryInital);
        Category category = categoryService.createCategory(categoryRequestDto);
        assertThat(category.getParent()).isNotNull();
    }

    @Test
    public void saveCategory_ShouldReturnCategoryNoParent_WhenCategoryNoParent() {
        categoryRequestDto.setParentId(2L);
        when(categoryRepository.findByName(categoryInital.getName())).thenReturn(java.util.Optional.ofNullable(null));
        when(categoryRepository.findById(2L)).thenReturn(java.util.Optional.of(categoryParent));
        categoryInital.setParent(null);
        when(categoryRepository.save(any(Category.class))).thenReturn(categoryInital);
        Category category = categoryService.createCategory(categoryRequestDto);
        assertThat(category.getParent()).isNull();
    }

    @Test
    public void updateCategory_shouldThrowNotFoundException_WhenCategoryNotFound() {
        //TODO
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(null));
        assertThrows(NotFoundException.class, () -> categoryService.updateCategory(categoryUpdateRequestDto));
    }
}
