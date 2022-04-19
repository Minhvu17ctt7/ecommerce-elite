package com.example.ecommercenashtechbackend.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class RefreshTokenRequestDto {
    @NotBlank(message = "Refresh token is required")
    String refreshToken;
}
