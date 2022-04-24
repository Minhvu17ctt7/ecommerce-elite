package com.example.ecommercenashtechbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateRequestDto {
    @NotNull(message = "Category id is required")
    private Long id;
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
