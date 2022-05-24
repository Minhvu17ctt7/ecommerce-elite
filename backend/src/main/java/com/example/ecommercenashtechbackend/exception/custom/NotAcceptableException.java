package com.example.ecommercenashtechbackend.exception.custom;

import org.springframework.http.HttpStatus;

// 406
public class NotAcceptableException extends RuntimeException {
    public static final HttpStatus status = HttpStatus.NOT_ACCEPTABLE;

    public NotAcceptableException(String message) {
        super(message);
    }
}
