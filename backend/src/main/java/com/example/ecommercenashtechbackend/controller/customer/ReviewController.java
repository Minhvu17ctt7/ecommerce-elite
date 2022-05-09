package com.example.ecommercenashtechbackend.controller.customer;

import com.example.ecommercenashtechbackend.dto.request.ReviewCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ReviewPaginationResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ReviewResponseDto;
import com.example.ecommercenashtechbackend.entity.Review;
import com.example.ecommercenashtechbackend.security.UserDetail;
import com.example.ecommercenashtechbackend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ResponseDto> createReview(@Validated @RequestBody ReviewCreateRequestDto reviewCreateRequestDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail userDetail = (UserDetail)principal;
        ReviewResponseDto reviewResponseDto = reviewService.createReview(reviewCreateRequestDto, userDetail.getUser().getId());
        ResponseDto<ReviewResponseDto> responseDto = new ResponseDto<>(200, reviewResponseDto, "Create review successful");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getListReviewFirstPaginationByProductId( @PathVariable("product") Long productId){
        return getListReviewPaginationByProductId(1, productId);
    }

    @GetMapping("/{product}/{page}")
    public ResponseEntity<ResponseDto> getListReviewPaginationByProductId(@PathVariable("page") int pageNumber, @PathVariable("product") Long productId){
        ReviewPaginationResponseDto reviewList = reviewService.getAllCategoriesPagination(pageNumber, productId);
        ResponseDto<ReviewPaginationResponseDto> responseDto = new ResponseDto<>(200, reviewList, "Get list review successfully");
        return ResponseEntity.ok(responseDto);
    }
}
