package com.example.ecommercenashtechbackend.dto.response;

import com.example.ecommercenashtechbackend.entity.OrderItem;
import com.example.ecommercenashtechbackend.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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

    private Set<OrderItemResponseDto> orderItems;

}
