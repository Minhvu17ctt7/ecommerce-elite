package com.example.ecommercenashtechbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product")
public class Product extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String alias;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "in_stock")
    private Long quantity;

    private float price;
    @Column(name = "discount_percent")
    private Float discountPercent;
    @Column(name = "main_image")
    private String mainImage;
    private boolean deleted = false;
    private float averageRating = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductImage> productImages = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductDetail> productDetails = new HashSet<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

}
