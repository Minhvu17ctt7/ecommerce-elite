package com.example.ecommercenashtechbackend.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {

    @NotBlank( message = "Phone is required")
    private String phone;
    @NotBlank(message = "First name is required ")
    private String firstName;
    @NotBlank(message = "Last name is required ")
    private String lastName;
    @NotBlank(message = "Address is required ")
    private String address;
    private String city;
    @NotBlank(message = "Country is required ")
    private String country;
    private List<CartItemRequestDto> cartItemList;

}
