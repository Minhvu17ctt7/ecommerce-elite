package com.example.ecommercenashtechbackend.exception;

import lombok.*;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String detail;
    private int status;

}
