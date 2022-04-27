package com.example.ecommercenashtechbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    @JsonProperty("refresh_token")
    String refreshToken;
    @JsonProperty("access_token")
    String accessToken;
}
