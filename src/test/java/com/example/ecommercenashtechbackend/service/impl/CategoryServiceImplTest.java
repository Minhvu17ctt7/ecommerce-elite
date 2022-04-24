//package com.example.ecommercenashtechbackend.service.impl;
//
//import com.example.ecommercenashtechbackend.dto.request.CategoryRequestDto;
//import com.example.ecommercenashtechbackend.entity.Category;
//import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
//import com.example.ecommercenashtechbackend.repository.CategoryRepository;
//import com.example.ecommercenashtechbackend.service.CategoryService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.modelmapper.ModelMapper;
//import org.webjars.NotFoundException;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//public class CategoryServiceImplTest {
//
//    public CategoryService categoryService;
//    public Category categoryInital;
//    public Category categoryParent;
//    public CategoryRepository categoryRepository;
//    public ModelMapper modelMapper;
//    CategoryRequestDto categoryRequestDto;
//
//    @BeforeEach
//    public void setUp() {
//        categoryRepository = Mockito.mock(CategoryRepository.class);
//        categoryService = new CategoryServiceImpl(categoryRepository);
//        categoryInital = Category.builder().id(1L).name("Laptop").alias("laptop").description("Model laptop").build();
//        categoryParent = Category.builder().id(2L).name("Laptop Parent").alias("laptop-parent").description("Model laptop parent").build();
//        categoryRequestDto = CategoryRequestDto.builder().name("Laptop").alias("laptop").description("Model laptop").build();
//    }
//
//    @Test
//    public void saveCategory_ShouldThrowConflictException_WhenCategoryAlreadyExists() {
//        //TODO
//        when(categoryRepository.findByName(categoryInital.getName())).thenReturn(java.util.Optional.ofNullable(categoryInital));
//        assertThrows(ConflictException.class, () -> categoryService.saveCategory(categoryRequestDto));
//    }
//
//    @Test
//    public void saveCategory_shouldThrowNotFoundException_WhenCategoryParentNotFound() {
//        //TODO
//        categoryRequestDto.setParentId(5L);
//        when(categoryRepository.findByName(categoryInital.getName())).thenReturn(java.util.Optional.ofNullable(null));
//        when(categoryRepository.findById(5L)).thenReturn(java.util.Optional.ofNullable(null));
//        assertThrows(NotFoundException.class, () -> categoryService.saveCategory(categoryRequestDto));
//    }
//
//    @Test
//    public void saveCategory_ShouldReturnCategoryHasParent_WhenCategoryParentExist() {
//        categoryRequestDto.setParentId(2L);
//        when(categoryRepository.findByName(categoryInital.getName())).thenReturn(java.util.Optional.ofNullable(null));
//        when(categoryRepository.findById(2L)).thenReturn(java.util.Optional.of(categoryParent));
//        categoryInital.setParent(categoryParent);
//        when(categoryRepository.save(any(Category.class))).thenReturn(categoryInital);
//        Category category = categoryService.saveCategory(categoryRequestDto);
//        assertThat(category.getParent()).isNotNull();
//    }
//
//    @Test
//    public void saveCategory_ShouldReturnCategoryNoParent_WhenCategoryNoParent() {
//        categoryRequestDto.setParentId(2L);
//        when(categoryRepository.findByName(categoryInital.getName())).thenReturn(java.util.Optional.ofNullable(null));
//        when(categoryRepository.findById(2L)).thenReturn(java.util.Optional.of(categoryParent));
//        categoryInital.setParent(null);
//        when(categoryRepository.save(any(Category.class))).thenReturn(categoryInital);
//        Category category = categoryService.saveCategory(categoryRequestDto);
//        assertThat(category.getParent()).isNull();
//    }
//
//    @Test
//    public void updateCategory_shouldThrowNotFoundException_WhenCategoryNotFound() {
//        //TODO
//        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(null));
//        assertThrows(NotFoundException.class, () -> categoryService.updateCategory(1L, categoryRequestDto));
//    }
//}
