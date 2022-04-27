package com.example.ecommercenashtechbackend.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatusRequestDto {

    @NotNull(message = "User status cannot be null")
    private Long id;
    @NotNull(message = "User status cannot be null")
    private boolean blocked;
}
