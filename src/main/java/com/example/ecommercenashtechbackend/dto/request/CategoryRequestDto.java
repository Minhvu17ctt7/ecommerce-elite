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
    @NotBlank(message = "Alias is required")
    private String alias;
    private String image;
    @NotBlank(message = "Description is required")
    private String description;
    @JsonProperty("parent_id")
    private Long parentId;
}
