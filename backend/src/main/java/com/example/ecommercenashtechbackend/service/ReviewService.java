package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.ReviewCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ReviewPaginationResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ReviewResponseDto;

import java.util.List;

public interface ReviewService {

    ReviewResponseDto createReview(ReviewCreateRequestDto reviewCreateRequestDto, Long userId);

    ReviewPaginationResponseDto getAllCategoriesPagination(int pageNumber, Long productId);
}
