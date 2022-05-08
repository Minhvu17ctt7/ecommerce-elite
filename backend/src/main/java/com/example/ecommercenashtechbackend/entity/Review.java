package com.example.ecommercenashtechbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="reviews")
public class Review extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private int rating;

    @ManyToOne
    @JoinColumn(name ="productId")
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name ="userId")
    private User user;
}
