package com.example.ecommercenashtechbackend.dto.response;

import com.example.ecommercenashtechbackend.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewPaginationResponseDto {
    private List<ReviewResponseDto> reviews;
    private int totalPage;
    private int sizePage;
}