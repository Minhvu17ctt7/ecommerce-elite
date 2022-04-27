package com.example.ecommercenashtechbackend.dto.request;

import com.example.ecommercenashtechbackend.entity.ProductDetail;
import com.example.ecommercenashtechbackend.entity.ProductImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateRequestDto {

    @NotNull(message = "Product id is required!")
    private Long id;
    @NotBlank(message = "Name product is required!")
    private String name;
    @NotBlank(message = "ALias product is required!")
    private String alias;
    @NotBlank(message = "Short description is required!")
    @JsonProperty("short_description")
    private String shortDescription;
    @NotBlank(message = "Full description is required!")
    @JsonProperty("full_description")
    private String fullDescription;
    @NotNull(message = "quantity is required!")
    private Long quantity;
    @NotNull(message = "Price product is required!")
    private float price;
    @NotBlank(message = "Main image is required!")
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
