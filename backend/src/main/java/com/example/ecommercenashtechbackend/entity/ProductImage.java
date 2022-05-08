package com.example.ecommercenashtechbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="product_images")
public class ProductImage extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    @ToString.Exclude
    @JsonIgnore
    private Product product;
}
