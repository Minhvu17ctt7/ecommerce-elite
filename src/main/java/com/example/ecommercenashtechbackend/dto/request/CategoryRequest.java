package com.example.ecommercenashtechbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @Column(length = 128, nullable = false, unique = true)
    private String name;
    @Column(length = 128, nullable = false)
    private String description;
}
