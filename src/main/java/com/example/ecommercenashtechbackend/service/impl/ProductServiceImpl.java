package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.ProductCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductCreateResponseDto;
import com.example.ecommercenashtechbackend.entity.Category;
import com.example.ecommercenashtechbackend.entity.Product;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.repository.CategoryRepository;
import com.example.ecommercenashtechbackend.repository.ProductRepository;
import com.example.ecommercenashtechbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductCreateResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Optional<Product> productOpt = productRepository.findByName(productCreateRequestDto.getName());
        if (!productOpt.isPresent()) {
            Optional<Category> categoryOpt = categoryRepository.findById(productCreateRequestDto.getCategoryId());
            if (categoryOpt.isPresent()) {
                Product productSave = modelMapper.map(productCreateRequestDto, Product.class);
                productSave.setCategory(categoryOpt.get());
                Product productSaved = productRepository.save(productSave);
                return modelMapper.map(productSaved, ProductCreateResponseDto.class);
            }
            throw new NotFoundException("Category not found");
        }
        throw new ConflictException("Product already exits");
    }
}
