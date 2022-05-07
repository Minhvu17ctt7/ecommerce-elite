package com.example.ecommercenashtechbackend.dto.response;

import com.example.ecommercenashtechbackend.entity.User;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {
    private String comment;
    private int rating;
    private User user;
}