package com.example.ecommercenashtechbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Category extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String alias;
    private String image;
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parentId")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();
}
