package com.example.ecommercenashtechbackend.controller.manage;

import com.example.ecommercenashtechbackend.dto.request.CategoryRequestDto;
import com.example.ecommercenashtechbackend.dto.request.CategoryUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.entity.Category;
import com.example.ecommercenashtechbackend.exception.ExceptionResponse;
import com.example.ecommercenashtechbackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryManageController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return getAllCategoriesPagination(1, 4, "name", "asc", null);
    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<List<Category>> getAllCategoriesPagination(@PathVariable int pageNumber, @RequestParam int pageSize,
                                                                     @RequestParam String sortField,
                                                                     @RequestParam String sortName, @RequestParam String keyword) {
        List<Category> listCategories = categoryService.getAllCategoriesPagination(pageNumber, pageSize, sortField, sortName, keyword);
        return ResponseEntity.ok(listCategories);
    }

    @Operation(summary = "Create category by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success, user registered",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Missing require field see message for more details",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "419", description = "Category already exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Category parent not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDto categoryRequest) {
        Category categorySaved = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(categorySaved);
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Category categorySaved = categoryService.updateCategory(categoryUpdateRequestDto);
        return ResponseEntity.ok(categorySaved);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category with id: " + id + " deleted");
    }
}
