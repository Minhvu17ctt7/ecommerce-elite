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
    private Category categoryExpected;
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
        categoryExpected = mock(Category.class);
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

    @Test
    public void updateCategory_ShouldReturnCategory_WhenUpdateSuccessNoParent() {
        Category categoryResult = mock(Category.class);
        when(categoryRepository.findById(categoryUpdateRequestDto.getId())).thenReturn(Optional.ofNullable(categoryInitial));
        when(categoryRepository.findByName(categoryUpdateRequestDto.getName())).thenReturn(Optional.ofNullable(null));
        when(modelMapper.map(categoryUpdateRequestDto, Category.class)).thenReturn(categoryExpected);
        when(categoryRepository.save(categoryExpected)).thenReturn(categoryResult);
        Category result = categoryService.updateCategory(categoryUpdateRequestDto);
        assertThat(result).isEqualTo(categoryResult);
    }

//    @Test
//    public void updateCategory_ShouldThrownNotFoundException_WhenFindCategoryParentByIdNotFound() {
//        CategoryUpdateRequestDto categoryUpdateRequest = CategoryUpdateRequestDto.builder().parentId(1L).build();
//        when(categoryRepository.findById(categoryUpdateRequest.getId())).thenReturn(Optional.ofNullable(categoryInitial));
//        when(categoryRepository.findByName(categoryUpdateRequest.getName())).thenReturn(Optional.ofNullable(null));
//        when(modelMapper.map(categoryUpdateRequest, Category.class)).thenReturn(categoryExpected);
//        when(categoryRepository.findById(categoryUpdateRequest.getId())).thenReturn(Optional.ofNullable(null));
//        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> categoryService.updateCategory(categoryUpdateRequest));
//        assertThat(notFoundException.getMessage()).isEqualTo("Parent category not found");
//    }

}
