package com.example.ecommercenashtechbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequestDto {
    @NotBlank(message = "Refresh token is required")
    String refreshToken;
}
