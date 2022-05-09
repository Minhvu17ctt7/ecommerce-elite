package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.ReviewCreateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ProductResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ReviewPaginationResponseDto;
import com.example.ecommercenashtechbackend.dto.response.ReviewResponseDto;
import com.example.ecommercenashtechbackend.entity.Product;
import com.example.ecommercenashtechbackend.entity.Review;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.repository.ProductRepository;
import com.example.ecommercenashtechbackend.repository.ReviewRepository;
import com.example.ecommercenashtechbackend.repository.UserRepository;
import com.example.ecommercenashtechbackend.service.ReviewService;
import com.example.ecommercenashtechbackend.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Util util;
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private final static int PAGE_SIZE_REVIEW = 4;


    @Override
    public ReviewResponseDto createReview(ReviewCreateRequestDto reviewCreateRequestDto, Long userId) {
        Optional<Product> productOptional = productRepository.findById(reviewCreateRequestDto.getProductId());
        if(!productOptional.isPresent()) throw new NotFoundException("Not found product with id: " + reviewCreateRequestDto.getProductId());
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()) throw new NotFoundException("Not found user with email: " + authentication.getName());
        Product product = productOptional.get();
        User user = userOptional.get();
        Review reviewSave = modelMapper.map(reviewCreateRequestDto, Review.class);
        float averageRating = (product.getAverageRating() + reviewCreateRequestDto.getRating()) / (product.getReviews().size() + 1);
        product.setAverageRating(averageRating);
        reviewSave.setProduct(product);
        reviewSave.setUser(user);
        Review reviewSaved = reviewRepository.save(reviewSave);
        ReviewResponseDto reviewResponseDto =  modelMapper.map(reviewSaved, ReviewResponseDto.class);
        return reviewResponseDto;
    }

    @Override
    public ReviewPaginationResponseDto getAllCategoriesPagination(int pageNumber, Long productId) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE_REVIEW);
        Page<Review> reviewList = reviewRepository.findAllByProductId(pageable, productId);
        List<ReviewResponseDto> reviewResponseDtoList =  util.mapList(reviewList.getContent(), ReviewResponseDto.class);
        return new ReviewPaginationResponseDto(reviewResponseDtoList, reviewList.getTotalPages(), 4);
    }
}
