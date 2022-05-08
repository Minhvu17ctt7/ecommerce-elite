package com.example.ecommercenashtechbackend.entity;

import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
}
