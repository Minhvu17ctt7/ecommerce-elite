package com.example.ecommercenashtechbackend.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponseDto {

    private Long id;
    private int quantity;
    private Long productId;
    private String nameProduct;
    private float priceProduct;
    private String mainImage;

}
