package com.example.ecommercenashtechbackend.dto.response;

import com.example.ecommercenashtechbackend.entity.Product;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Set<Product> products;
}
