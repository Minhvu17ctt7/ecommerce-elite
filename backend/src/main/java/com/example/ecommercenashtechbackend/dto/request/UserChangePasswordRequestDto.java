package com.example.ecommercenashtechbackend.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangePasswordRequestDto {

    @NotBlank(message = "New password is required")
    private String password;
    @NotBlank(message = "Old password is required")
    private String oldPassword;
}
