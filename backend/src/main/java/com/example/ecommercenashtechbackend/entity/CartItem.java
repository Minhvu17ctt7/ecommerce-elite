package com.example.ecommercenashtechbackend.entity;

import lombok.*;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class CartItem extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "productId", nullable=false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

}
