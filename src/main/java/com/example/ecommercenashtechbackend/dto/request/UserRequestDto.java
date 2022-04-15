package com.example.ecommercenashtechbackend.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

    @Email(message = "Email invalid")
    @NotBlank(message = "Missing email")
    private String email;
    @NotBlank(message = "Missing password")
    private String password;
    @JsonProperty("first_name")
    @NotBlank(message = "Missing first name")
    private String firstName;
    @JsonProperty("last_name")
    @NotBlank(message = "Missing last name")
    private String lastName;
    private String photo;
    private String role;

}

