package com.example.ecommercenashtechbackend.exception.custom;

import org.springframework.http.HttpStatus;

// 403
public class ForbiddenException extends RuntimeException {
    public static final HttpStatus status = HttpStatus.FORBIDDEN;

    public ForbiddenException(String message) {
        super(message);
    }
}
