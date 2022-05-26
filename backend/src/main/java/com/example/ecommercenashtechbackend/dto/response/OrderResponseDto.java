package com.example.ecommercenashtechbackend.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Long id;
    private Double totalPrice;
    private Long totalItem;
    private String address;
    private String phone;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String status = "Start";
    private LocalDateTime createdDate;

    private Set<OrderItemResponseDto> orderItems;

}
