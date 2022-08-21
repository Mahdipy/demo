package com.neshan.demo.Domain;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(
            name = "role_id",
            updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(
            name = "role_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String rolename;

}
