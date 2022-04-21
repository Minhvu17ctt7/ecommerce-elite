package com.example.ecommercenashtechbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
