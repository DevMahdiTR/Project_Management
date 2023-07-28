package com.example.pfe_project.models.training_session;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "training_Sessions")
@Data
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description",nullable = false)
    private String description;


    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TrainingSessionUser> enrollmentRequests = new ArrayList<>();

}
