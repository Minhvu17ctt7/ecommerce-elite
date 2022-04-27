package com.example.ecommercenashtechbackend.dto.response;

import com.example.ecommercenashtechbackend.entity.ProductDetail;
import com.example.ecommercenashtechbackend.entity.ProductImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private String alias;
    @JsonProperty("short_description")
    private String shortDescription;
    @JsonProperty("full_description")
    private String fullDescription;
    private Long quantity;
    private float price;
    @JsonProperty("main_image")
    private String mainImage;
    @JsonProperty("discount_percent")
    private float discountPercent;
    @JsonProperty("category_id")
    private Long categoryId;
    @JsonProperty("product_images")
    private Set<ProductImage> productImages = new HashSet<>();
    @JsonProperty("product_details")
    private Set<ProductDetail> productDetails = new HashSet<>();
}
