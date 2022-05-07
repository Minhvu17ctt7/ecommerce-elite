package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.ProductCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.request.ProductUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductPaginationResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.entity.Category;
import com.example.ecommercenashtechbackend.entity.Product;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.repository.CategoryRepository;
import com.example.ecommercenashtechbackend.repository.ProductRepository;
import com.example.ecommercenashtechbackend.service.ProductService;
import com.example.ecommercenashtechbackend.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Util util;

    @Override
    public ProductResponseDto getProductDetail(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(!productOptional.isPresent()) {
            throw new NotFoundException("Not found product witd id: "+ id);
        }
        return modelMapper.map(productOptional.get(), ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Optional<Product> productOpt = productRepository.findByName(productCreateRequestDto.getName());
        if (!productOpt.isPresent()) {
            Product productSave = modelMapper.map(productCreateRequestDto, Product.class);
            return save(productSave, productCreateRequestDto.getCategoryId());
        }
        throw new ConflictException("Product name already exits");
    }


//    @Override
//    public List<ProductResponseDto> getAllCategoriesPagination(int pageNumber, int pageSize, String sortField, String sortName, String keywork, boolean deleted) {
//        Sort sort = Sort.by(sortField);
//        sort = sortName.equals("asc") ? sort.ascending() : sort.descending();
//        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
//        List<Product> productList = new ArrayList<>();
//        if (keywork != null) {
//            productList = productRepository.findAll(keywork, deleted, pageable).getContent();
//        } else {
//            productList = productRepository.findAll(pageable).getContent();
//        }
//        List<ProductResponseDto> result =  util.mapList(productList, ProductResponseDto.class);
//        return result;
//    }

    @Override
    public ProductPaginationResponseDto getAllCategoriesPagination(int pageNumber, int pageSize, String sortField, String sortName, String keywork, boolean deleted) {
        Sort sort = Sort.by(sortField);
        sort = sortName.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<Product> pageProductList;
        if (keywork != null) {
            pageProductList = productRepository.findAll(keywork, deleted, pageable);
        } else {
            pageProductList = productRepository.findAll(pageable);
        }
        List<ProductResponseDto> productResponseDtoList =  util.mapList(pageProductList.getContent(), ProductResponseDto.class);
        ProductPaginationResponseDto result = new ProductPaginationResponseDto(productResponseDtoList, pageProductList.getTotalPages(), 8);
        return result;
    }

    @Override
    public ProductResponseDto updateProduct(ProductUpdateRequestDto productUpdateRequestDto) {
        Optional<Product> productOldOpt = productRepository.findById(productUpdateRequestDto.getId());
        if (productOldOpt.isPresent()) {
            Product productOld = productOldOpt.get();
            Optional<Product> productExist = productRepository.findByName(productUpdateRequestDto.getName());
            if (!productExist.isPresent() || productExist.get().getId() != productOld.getId()) {
                Product productSave = modelMapper.map(productUpdateRequestDto, Product.class);
                return save(productSave, productUpdateRequestDto.getCategoryId());
            }
            throw new ConflictException("Product name already exits");
        }
        throw new NotFoundException("Product not found");
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            productRepository.updateDeletedProductById(productOpt.get().getId());
        } else {
            throw new NotFoundException("Product not found");
        }
    }

    ProductResponseDto save(Product productSave, Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isPresent()) {
            productSave.setCategory(categoryOpt.get());
            Product productSaved = productRepository.save(productSave);
            return modelMapper.map(productSaved, ProductResponseDto.class);
        }
        throw new NotFoundException("Category not found");
    }
}
