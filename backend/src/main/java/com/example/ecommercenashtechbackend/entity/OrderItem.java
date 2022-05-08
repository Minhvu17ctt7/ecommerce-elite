package com.example.ecommercenashtechbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class OrderItem extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private double price;
    private double name;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;
}

