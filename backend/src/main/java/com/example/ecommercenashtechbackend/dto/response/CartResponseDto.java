package com.example.ecommercenashtechbackend.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDto {

    private int quantity;
    private Long productId;

}
