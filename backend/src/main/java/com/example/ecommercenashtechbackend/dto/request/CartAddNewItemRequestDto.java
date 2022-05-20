package com.example.ecommercenashtechbackend.dto.request;

import com.example.ecommercenashtechbackend.entity.Product;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartAddNewItemRequestDto {

    private int quantity;
    private Long productId;

}
