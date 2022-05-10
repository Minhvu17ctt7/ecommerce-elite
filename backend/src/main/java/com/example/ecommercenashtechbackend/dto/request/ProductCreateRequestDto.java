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
public class ProductCreateRequestDto {

    @NotBlank(message = "Name product is required!")
    private String name;
    private String alias;
    @NotBlank(message = "Short description is required!")
    private String shortDescription;
    @NotBlank(message = "Full description is required!")
    private String fullDescription;
    @NotNull(message = "quantity is required!")
    private Long quantity;
    @NotNull(message = "Price product is required!")
    private float price;
    @NotBlank(message = "Main image is required!")
    private String mainImage;
    private float discountPercent;
    @NotNull(message = "Category is required!")
    private Long categoryId;
    private Set<ProductImage> productImages = new HashSet<>();
    private Set<ProductDetail> productDetails = new HashSet<>();
}
