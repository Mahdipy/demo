package com.neshan.demo.Domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(
            name = "user_id",
            updatable = false
    )
    private int id;

    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String username;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private Role role;
}
