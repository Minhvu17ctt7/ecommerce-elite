package com.example.ecommercenashtechbackend.dto.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCreateRequestDto {

    private Long productId;
    @Min(value = 1, message = "Rating invalid")
    @Max(value = 5, message = "Rating invalid")
    private int rating;
    private String comment;
}
