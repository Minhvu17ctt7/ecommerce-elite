package com.example.ecommercenashtechbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDto {

    private Long id;
    @JsonProperty("email")
    private String email;
    private String firstName;
    private String lastName;
    private String photo;
    private boolean blocked;
    private String refreshToken;
    private String accessToken;
}
