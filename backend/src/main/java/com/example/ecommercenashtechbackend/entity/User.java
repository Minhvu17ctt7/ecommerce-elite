package com.example.ecommercenashtechbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String photo;

    private boolean blocked = false;
    private boolean deleted = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;
}
