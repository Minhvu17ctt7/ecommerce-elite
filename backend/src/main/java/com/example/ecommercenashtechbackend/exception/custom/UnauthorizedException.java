package com.example.ecommercenashtechbackend.exception.custom;

import org.springframework.http.HttpStatus;

//401
public class UnauthorizedException extends RuntimeException  {
    public static final HttpStatus status = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException(String message) {
        super(message);
    }
}
