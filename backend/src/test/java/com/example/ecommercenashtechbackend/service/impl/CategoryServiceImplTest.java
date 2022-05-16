package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.CategoryRequestDto;
import com.example.ecommercenashtechbackend.dto.request.CategoryUpdateRequestDto;
import com.example.ecommercenashtechbackend.entity.Category;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.repository.CategoryRepository;
import com.example.ecommercenashtechbackend.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.webjars.NotFoundException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private Util util;
    private CategoryServiceImpl categoryService;
    private Category categoryInitial;
    private CategoryRequestDto categoryRequestDto;
    private CategoryUpdateRequestDto categoryUpdateRequestDto;

    @BeforeEach
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        modelMapper = mock(ModelMapper.class);
        util = mock(Util.class);
        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper, util);
        categoryInitial = mock(Category.class);
        categoryRequestDto = mock(CategoryRequestDto.class);
        categoryUpdateRequestDto = mock(CategoryUpdateRequestDto.class);
    }

    @Test
    public void updateCategory_ShouldThrownNotFoundException_WhenFindCategoryByIdNotExist() {
        when(categoryRepository.findById(categoryUpdateRequestDto.getId())).thenReturn(Optional.ofNullable(null));
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> categoryService.updateCategory(categoryUpdateRequestDto));
        assertThat(notFoundException.getMessage()).isEqualTo("Category not found");
    }

    @Test
    public void updateCategory_ShouldThrownConflictException_WhenFindCategoryByIdNotExist() {
        Category categoryExisted = Category.builder().id(1L).build();
        when(categoryRepository.findById(categoryUpdateRequestDto.getId())).thenReturn(Optional.ofNullable(categoryInitial));
        when(categoryRepository.findByName(categoryUpdateRequestDto.getName())).thenReturn(Optional.ofNullable(categoryExisted));
        ConflictException conflictException = assertThrows(ConflictException.class, () -> categoryService.updateCategory(categoryUpdateRequestDto));
        assertThat(conflictException.getMessage()).isEqualTo("Category name already exits");
    }

}
