package com.example.ecommercenashtechbackend.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto<T> {
    private int status;
    private T data;
    private String message;
}
