package com.example.ecommercenashtechbackend.dto.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemRequestDto {

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must greater than zero")
    private int quantity;
    @NotNull(message = "Quantity is required")
    private Long productId;

}
