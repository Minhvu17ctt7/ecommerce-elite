package com.example.ecommercenashtechbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    private String alias;
    private String image;
    private String description;
    private Long parentId;
}
