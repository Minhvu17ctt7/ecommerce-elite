package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.CategoryRequestDto;
import com.example.ecommercenashtechbackend.dto.request.CategoryUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.CategoryResponseDto;
import com.example.ecommercenashtechbackend.entity.Category;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final Util util;

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> listCategory =  categoryRepository.findAll();
        return util.mapList(listCategory, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto findCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()) {
            throw new NotFoundException("Not found category");
        }
        return modelMapper.map(categoryOptional.get(), CategoryResponseDto.class);
    }

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
        Optional<Category> cateOpt = categoryRepository.findByName(categoryRequestDto.getName());
        if (!cateOpt.isPresent()) {
            Category categorySave = new Category();
            return save(categorySave, modelMapper.map(categoryRequestDto, CategoryUpdateRequestDto.class));
        }
        throw new ConflictException("Category already exists");
    }

    @Override
    public Category updateCategory(CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryUpdateRequestDto.getId());
        if (categoryOpt.isPresent()) {
            Category categoryOld = categoryOpt.get();
            return save(categoryOld, categoryUpdateRequestDto);
        }
        throw new NotFoundException("Category not found");
    }

    Category save(Category category, CategoryUpdateRequestDto categoryRequestDto) {
        category = modelMapper.map(categoryRequestDto, Category.class);
        if (categoryRequestDto.getParentId() != null) {
            Optional<Category> categoryParentOpt = categoryRepository.findById(categoryRequestDto.getParentId());
            if (categoryParentOpt.isPresent()) {
                category.setParent(categoryParentOpt.get());
            } else {
                throw new NotFoundException("Parent category not found");
            }
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            categoryRepository.deleteById(id);
            return;
        }
        throw new NotFoundException("Category not found");
    }

}
