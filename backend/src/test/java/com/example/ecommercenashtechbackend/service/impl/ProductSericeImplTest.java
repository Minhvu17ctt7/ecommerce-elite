package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.ProductCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.request.ProductUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.entity.Category;
import com.example.ecommercenashtechbackend.entity.Product;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.repository.CategoryRepository;
import com.example.ecommercenashtechbackend.repository.ProductRepository;
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

public class ProductSericeImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productServiceImpl;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private Util util;
    private Product productInitial;
    private ProductCreateRequestDto productCreateRequestDto;
    private ProductUpdateRequestDto productUpdateRequestDto;
    private Product productExpected;
    private Category category;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        modelMapper = mock(ModelMapper.class);
        util = mock(Util.class);
        productInitial = mock(Product.class);
        productCreateRequestDto = mock(ProductCreateRequestDto.class);
        productUpdateRequestDto = mock(ProductUpdateRequestDto.class);
        productServiceImpl = new ProductServiceImpl(productRepository, categoryRepository, modelMapper, util);
        productExpected = mock(Product.class);
        category = mock(Category.class);
        productResponseDto = mock(ProductResponseDto.class);
    }

    @Test
    public void createProduct_ShouldThrownConflictException_WhenFindProductByNameNotExist() {
        when(productRepository.findByName(productCreateRequestDto.getName())).thenReturn(Optional.ofNullable(productExpected));
        ConflictException conflictException = assertThrows(ConflictException.class, () -> productServiceImpl.createProduct(productCreateRequestDto));
        assertThat(conflictException.getMessage()).isEqualTo("Product name already exits");
    }

    @Test
    public void createProduct_ShouldThrownNotFoundException_WhenFindCategoryByIdNotFound() {
        when(productRepository.findByName(productCreateRequestDto.getName())).thenReturn(Optional.ofNullable(null));
        when(modelMapper.map(productCreateRequestDto, Product.class)).thenReturn(productExpected);
        when(categoryRepository.findById(productCreateRequestDto.getCategoryId())).thenReturn(Optional.ofNullable(null));
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> productServiceImpl.createProduct(productCreateRequestDto));
        assertThat(notFoundException.getMessage()).isEqualTo("Category not found");
    }

    @Test
    public void createProduct_ShouldReturnProductResponseDto_WhenCreateSuccess() {
        when(productRepository.findByName(productCreateRequestDto.getName())).thenReturn(Optional.ofNullable(null));
        when(modelMapper.map(productCreateRequestDto, Product.class)).thenReturn(productExpected);
        when(categoryRepository.findById(productCreateRequestDto.getCategoryId())).thenReturn(Optional.ofNullable(category));
        when(productRepository.save(productExpected)).thenReturn(productExpected);
        when(modelMapper.map(productExpected, ProductResponseDto.class)).thenReturn(productResponseDto);
        ProductResponseDto result = productServiceImpl.createProduct(productCreateRequestDto);
        assertThat(result).isEqualTo(productResponseDto);
    }

    @Test
    public void updateProduct_ShouldThrownNotFoundException_WhenFindProductByIdNotFound() {
        when(productRepository.findById(productUpdateRequestDto.getId())).thenReturn(Optional.ofNullable(null));
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> productServiceImpl.updateProduct(productUpdateRequestDto));
        assertThat(notFoundException.getMessage()).isEqualTo("Product not found");
    }

    @Test
    public void updateProduct_ShouldThrownConflictException_WhenFindProductByNameExitsAndProductOldNotEqualProductExits() {
        Product productOld = Product.builder().id(1L).build();
        Product productExist = Product.builder().id(2L).build();
        when(productRepository.findById(productUpdateRequestDto.getId())).thenReturn(Optional.ofNullable(productOld));
        when(productRepository.findByName(productUpdateRequestDto.getName())).thenReturn(Optional.ofNullable(productExist));
        ConflictException conflictException = assertThrows(ConflictException.class, () -> productServiceImpl.updateProduct(productUpdateRequestDto));
        assertThat(conflictException.getMessage()).isEqualTo("Product name already exits");
    }

    @Test
    public void updateProduct_ShouldThrownNotFoundException_WhenFindCategoryByIdNotFound() {
        Product productOld = mock(Product.class);
        Product productExist = mock(Product.class);
        when(productRepository.findById(productUpdateRequestDto.getId())).thenReturn(Optional.ofNullable(productOld));
        when(productRepository.findByName(productUpdateRequestDto.getName())).thenReturn(Optional.ofNullable(productExist));
        when(modelMapper.map(productUpdateRequestDto, Product.class)).thenReturn(productInitial);
        when(categoryRepository.findById(productUpdateRequestDto.getCategoryId())).thenReturn(Optional.ofNullable(null));
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> productServiceImpl.updateProduct(productUpdateRequestDto));
        assertThat(notFoundException.getMessage()).isEqualTo("Category not found");
    }

    @Test
    public void updateProduct_ShouldProductResponseDto_WhenUpdateSuccess() {
        Product productOld = mock(Product.class);
        Product productExist = mock(Product.class);
        when(productRepository.findById(productUpdateRequestDto.getId())).thenReturn(Optional.ofNullable(productOld));
        when(productRepository.findByName(productUpdateRequestDto.getName())).thenReturn(Optional.ofNullable(productExist));
        when(modelMapper.map(productUpdateRequestDto, Product.class)).thenReturn(productInitial);
        when(categoryRepository.findById(productUpdateRequestDto.getCategoryId())).thenReturn(Optional.ofNullable(category));
        when(productRepository.save(productInitial)).thenReturn(productExpected);
        when(modelMapper.map(productExpected, ProductResponseDto.class)).thenReturn(productResponseDto);
        ProductResponseDto result = productServiceImpl.updateProduct(productUpdateRequestDto);
        assertThat(result).isEqualTo(productResponseDto);
    }

}
