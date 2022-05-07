package com.example.ecommercenashtechbackend.dto.response;

import com.example.ecommercenashtechbackend.entity.ProductDetail;
import com.example.ecommercenashtechbackend.entity.ProductImage;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProductDetailResponseDto {

    private Long id;
    private String name;
    private String alias;
    private String shortDescription;
    private String fullDescription;
    private Long quantity;
    private float price;
    private String mainImage;
    private float discountPercent;
    private Long categoryId;
    private Set<ProductImage> productImages = new HashSet<>();
    private Set<ProductDetail> productDetails = new HashSet<>();
}
