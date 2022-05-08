package com.example.ecommercenashtechbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class ProductDetail extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    @JsonIgnore
    private Product product;

}
