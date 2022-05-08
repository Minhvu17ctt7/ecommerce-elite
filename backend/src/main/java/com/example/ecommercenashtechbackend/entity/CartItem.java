package com.example.ecommercenashtechbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class CartItem extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @OneToOne
    @JoinColumn(name ="categoryId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

}
