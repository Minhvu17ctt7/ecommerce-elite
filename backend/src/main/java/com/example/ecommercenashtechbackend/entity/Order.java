package com.example.ecommercenashtechbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalPrice = 0d;
    private Long totalItem = 0l;
    private String address;
    private String phone;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String status = "Start";
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Set<OrderItem> orderItems = new HashSet<>();

}
