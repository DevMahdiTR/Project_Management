package com.example.pfe_project.models.role;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "roles")
public class Role {
    public Role(String name)
    {
        this.name = name;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name ="id", unique = true)
    private int id;
    @Column(name ="name" ,unique = true, nullable = false)
    @NotBlank
    private String name;

    public Role() {

    }
}
