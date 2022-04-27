package com.example.ecommercenashtechbackend.exception.custom;

import org.springframework.http.HttpStatus;

// 409
public class ConflictException extends RuntimeException {
    public static final HttpStatus status = HttpStatus.CONFLICT;

    public ConflictException(String message) {
        super(message);
    }

}
