package com.example.ecommercenashtechbackend.controller.manage;

import com.example.ecommercenashtechbackend.dto.request.CategoryRequestDto;
import com.example.ecommercenashtechbackend.dto.request.CategoryUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.*;
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

    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllCategoriesPagination(@RequestParam boolean deleted) {
        List<CategoryResponseDto> listCategories = categoryService.getAllCategories(deleted);
        ResponseDto<List<CategoryResponseDto>> responseDto = new ResponseDto<>(200, listCategories, "Get categories user successfully");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllCategoriesFirstPage() {
        return getAllCategoriesPagination(1, 4, "name", "asc", null);
    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<ResponseDto> getAllCategoriesPagination(@PathVariable int pageNumber, @RequestParam int pageSize,
                                                                     @RequestParam String sortField,
                                                                     @RequestParam String sortName, @RequestParam String keyword) {
        List<Category> listCategories = categoryService.getAllCategoriesPagination(pageNumber, pageSize, sortField, sortName, keyword);
        ResponseDto<List<Category>> responseDto = new ResponseDto<>(200, listCategories, "Get categories user successfully");
        return ResponseEntity.ok(responseDto);
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
    @PostMapping
    public ResponseEntity<ResponseDto> createCategory(@RequestBody CategoryRequestDto categoryRequest) {
        Category categorySaved = categoryService.createCategory(categoryRequest);
        ResponseDto<Category> responseDto = new ResponseDto<>(200, categorySaved, "Create category successfully");
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateCategory(@RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Category categorySaved = categoryService.updateCategory(categoryUpdateRequestDto);
        ResponseDto<Category> responseDto = new ResponseDto<>(200, categorySaved, "Update category successfully");
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        ResponseDto<Long> responseDto = new ResponseDto<>(200, id, "Category with id: " + id + " deleted");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}/check-available-delete")
    public ResponseEntity<ResponseDto> checkAvailableDelete(@PathVariable("id") Long id) {
        boolean result = categoryService.checkAvailableDelete(id);
        ResponseDto<CheckAvalableDeleteResponseDto> responseDto = new ResponseDto<>(200, new CheckAvalableDeleteResponseDto(result), "Category with id: " + id + " available delete");
        return ResponseEntity.ok(responseDto);
    }

}
