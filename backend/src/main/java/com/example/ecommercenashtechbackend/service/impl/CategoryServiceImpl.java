package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.CategoryRequestDto;
import com.example.ecommercenashtechbackend.dto.request.CategoryUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.CategoryResponseDto;
import com.example.ecommercenashtechbackend.entity.Category;
import com.example.ecommercenashtechbackend.entity.Product;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.repository.CategoryRepository;
import com.example.ecommercenashtechbackend.service.CategoryService;
import com.example.ecommercenashtechbackend.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Util util;

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> listCategory = categoryRepository.findAll();
        return util.mapList(listCategory, CategoryResponseDto.class);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories(boolean deleted) {
        List<Category> listCategory = categoryRepository.findAllByDeleted(deleted);
        return util.mapList(listCategory, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto findCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        categoryOptional.orElseThrow(() -> new NotFoundException("Category with id: " + id + " not found"));
        return modelMapper.map(categoryOptional.get(), CategoryResponseDto.class);
    }

    //Get categories native query
    @Override
    public List<Category> getAllCategoriesPagination(int pageNumber, int pageSize, String sortField, String sortName, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortName.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        if (keyword != null) {
            return categoryRepository.findByNameContaining(keyword, pageable).getContent();
        }
        return categoryRepository.findAll(pageable).getContent();
    }

    @Override
    public Category createCategory(CategoryRequestDto categoryRequestDto) {
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryRequestDto.getName());
        if (!categoryOptional.isPresent()) {
            Category categorySave = new Category();
            return save(categorySave, modelMapper.map(categoryRequestDto, CategoryUpdateRequestDto.class));
        }
        throw new ConflictException("Category already exists");
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryUpdateRequestDto.getId());
        categoryOptional.orElseThrow(() -> new NotFoundException("Category not found"));
        Category categoryOld = categoryOptional.get();
        Optional<Category> categoryExist = categoryRepository.findByName(categoryUpdateRequestDto.getName());
        if (categoryExist.isPresent() && categoryExist.get().getId() != categoryOld.getId()) {
            throw new ConflictException("Category name already exits");
        }
        return save(categoryOld, categoryUpdateRequestDto);
    }

    private CategoryResponseDto save(Category category, CategoryUpdateRequestDto categoryRequestDto) {
        category = modelMapper.map(categoryRequestDto, Category.class);
        if (categoryRequestDto.getParentId() != null) {
            Optional<Category> categoryParentOptional = categoryRepository.findById(categoryRequestDto.getParentId());
            categoryParentOptional.orElseThrow(() -> new NotFoundException("Parent category not found"));
            category.setParent(categoryParentOptional.get());
        }
        Category categorySaved =  categoryRepository.save(category);
        return modelMapper.map(categorySaved, CategoryResponseDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            categoryRepository.updateDeletedProductById(id);
            return;
        }
        throw new NotFoundException("Category not found");
    }

    @Override
    public boolean checkAvailableDelete(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndDeleted(categoryId, false);
        if (categoryOptional.isPresent()) {
            if (categoryOptional.get().getProducts().size() > 0) {
                return false;
            }
            return true;
        }
        throw new NotFoundException("Category with id: " + categoryId + " not found");
    }

}
