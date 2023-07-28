package com.example.pfe_project.models.training_session;


import com.example.pfe_project.models.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "training_session_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSessionUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "training_session_id")
    private TrainingSession trainingSession;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;


}