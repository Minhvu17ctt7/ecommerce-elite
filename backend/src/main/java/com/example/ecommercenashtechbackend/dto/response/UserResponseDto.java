package com.example.ecommercenashtechbackend.dto.response;

import com.example.ecommercenashtechbackend.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String photo;
    private boolean blocked;
    private Set<Role> roles;
}