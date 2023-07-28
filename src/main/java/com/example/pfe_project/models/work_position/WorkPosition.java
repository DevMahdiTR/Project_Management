package com.example.pfe_project.models.work_position;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_position")
public class WorkPosition {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;


    @Column (name ="position_name", unique = true,nullable = false)
    private String positionName;

    @Column(name = "salary")
    private double salary;


    public WorkPosition(String positionName, double salary) {
        this.positionName = positionName;
        this.salary = salary;
    }
}
