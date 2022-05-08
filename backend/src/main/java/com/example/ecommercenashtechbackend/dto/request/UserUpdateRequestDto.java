package com.example.ecommercenashtechbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestDto {

    @NotNull(message = "Id is required")
    private Long id;
    private String firstName;
    private String lastName;
    private String photo;
    private String role;
}