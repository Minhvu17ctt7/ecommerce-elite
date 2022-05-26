package com.example.ecommercenashtechbackend.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponseDto<T> {
    private List<T> data;
    private int totalPage;
    private int sizePage;
}
