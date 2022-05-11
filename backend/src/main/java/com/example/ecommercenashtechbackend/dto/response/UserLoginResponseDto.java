package com.example.ecommercenashtechbackend.dto.response;

import com.example.ecommercenashtechbackend.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String photo;
    private boolean blocked;
    private String refreshToken;
    private String accessToken;
    private Set<Role> roles;
}
