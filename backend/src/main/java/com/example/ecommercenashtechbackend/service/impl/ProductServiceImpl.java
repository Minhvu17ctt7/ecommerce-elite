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
import com.example.ecommercenashtechbackend.repository.specification.ProductSpecificationsBuilder;
import com.example.ecommercenashtechbackend.repository.specification.SearchCriteria;
import com.example.ecommercenashtechbackend.service.ProductService;
import com.example.ecommercenashtechbackend.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
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
        productOptional.orElseThrow(() -> new NotFoundException("Not found product with id: " + id));
        return modelMapper.map(productOptional.get(), ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getAllProducts(boolean deleted) {
        List<Product> productList = productRepository.findAllByDeleted(deleted);
        List<ProductResponseDto> result = util.mapList(productList, ProductResponseDto.class);
        return result;
    }

    @Override
    public ProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Optional<Product> productOpt = productRepository.findByName(productCreateRequestDto.getName());
        if (productOpt.isEmpty()) {
            Product productSave = modelMapper.map(productCreateRequestDto, Product.class);
            return save(productSave, productCreateRequestDto.getCategoryId());
        }
        throw new ConflictException("Product name already exits");
    }

    //Get all product pagination with native query
    @Override
    public ProductPaginationResponseDto getAllProductsPagination(int pageNumber, int pageSize, String sortField, String sortName, String keywork, boolean deleted) {
        Sort sort = Sort.by(sortField);
        sort = sortName.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<Product> pageProductList;
        if (keywork != null) {
            pageProductList = productRepository.findAll(keywork, deleted, pageable);
        } else {
            pageProductList = productRepository.findAll(pageable);
        }
        List<ProductResponseDto> productResponseDtoList = util.mapList(pageProductList.getContent(), ProductResponseDto.class);
        ProductPaginationResponseDto result = new ProductPaginationResponseDto(productResponseDtoList, pageProductList.getTotalPages(), 8);
        return result;
    }

    @Override
    public ProductPaginationResponseDto getAllProductsPaginationBySpecification(int pageNumber, int pageSize, String sortField, String sortName, String search, boolean deleted) {
        Sort sort = Sort.by(sortField);
        sort = sortName.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        List<SearchCriteria> searchCriteriaList = util.buildListProductSpecifications(search);
        convertSearchCriteriaCategoryId(searchCriteriaList);
        SearchCriteria deletedCriteria = new SearchCriteria("deleted", ":", deleted, false);
        searchCriteriaList.add(deletedCriteria);
        ProductSpecificationsBuilder builder = new ProductSpecificationsBuilder(searchCriteriaList);
        Specification<Product> spec = builder.build();
        Page<Product> pageProductList = productRepository.findAll(spec, pageable);
        List<ProductResponseDto> productResponseDtoList = util.mapList(pageProductList.getContent(), ProductResponseDto.class);
        ProductPaginationResponseDto result = new ProductPaginationResponseDto(productResponseDtoList, pageProductList.getTotalPages(), 8);
        return result;
    }

    protected void convertSearchCriteriaCategoryId(List<SearchCriteria> searchCriteriaList) {
        for (int i = 0; i < searchCriteriaList.size(); i++) {
            SearchCriteria searchCriteria = searchCriteriaList.get(i);
            if (searchCriteria.getKey().equals("category")) {
                String stringToConvert = String.valueOf(searchCriteria.getValue());
                Long categoryId = Long.parseLong(stringToConvert);
                Category category = Category.builder().id(categoryId).build();
                searchCriteria.setValue(category);
            }
        }
    }

    @Override
    public ProductResponseDto updateProduct(ProductUpdateRequestDto productUpdateRequestDto) {
        Optional<Product> productOldOptional = productRepository.findById(productUpdateRequestDto.getId());
        productOldOptional.orElseThrow(() -> new NotFoundException("Product not found"));
        Product productOld = productOldOptional.get();
        Optional<Product> productExist = productRepository.findByName(productUpdateRequestDto.getName());
        if (productExist.isPresent() && productExist.get().getId() != productOld.getId()) {
            throw new ConflictException("Product name already exits");
        }
        Product productSave = modelMapper.map(productUpdateRequestDto, Product.class);
        return save(productSave, productUpdateRequestDto.getCategoryId());
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> productOldOptional = productRepository.findById(id);
        productOldOptional.orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.updateDeletedProductById(productOldOptional.get().getId());
    }

    protected ProductResponseDto save(Product productSave, Long categoryId) {
        Optional<Category> productOldOptional = categoryRepository.findById(categoryId);
        productOldOptional.orElseThrow(() -> new NotFoundException("Category not found"));
        productSave.setCategory(productOldOptional.get());
        Product productSaved = productRepository.save(productSave);
        return modelMapper.map(productSaved, ProductResponseDto.class);
    }
}
