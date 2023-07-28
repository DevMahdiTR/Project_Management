package com.example.pfe_project.models.holiday;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "holiday_type")
public class HolidayType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "holiday_name",unique = true,nullable = false)
    @JsonProperty("holiday_name")
    private String holidayName;

    public HolidayType(){}
    public  HolidayType(String holidayName){
        this.holidayName = holidayName;
    }

    @OneToMany(mappedBy = "holidayType", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<HolidayRequest> holidayRequestList = new ArrayList<>();
}
