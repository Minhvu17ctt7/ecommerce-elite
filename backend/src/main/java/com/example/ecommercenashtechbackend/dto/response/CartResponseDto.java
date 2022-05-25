package com.example.ecommercenashtechbackend.dto.response;

import com.example.ecommercenashtechbackend.entity.CartItem;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDto {

    private Long id;
    private Double totalPrice = 0D;
    private Long totalItem = 0L;
    private Set<CartItemResponseDto> cartItems;

}
