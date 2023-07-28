package com.example.pfe_project.models.holiday;

import com.example.pfe_project.dto.holiday.HolidayRequestDto;
import com.example.pfe_project.models.training_session.EnrollmentStatus;
import com.example.pfe_project.models.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "holiday_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus requestStatus;

    @Column(name ="description",nullable = false)
    private String description;
    @Column(name = "starting_date",nullable = false)
    private Date startingDate;
    @Column(name = "ending_date",nullable = false)
    private Date endingDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "holiday_type_id")
    private HolidayType holidayType;




    public HolidayRequest(HolidayRequestDto holidayRequestDto)
    {
        this.description = holidayRequestDto.getDescription();
        this.startingDate = holidayRequestDto.getStatingDate();
        this.endingDate = holidayRequestDto.getEndingDate();
    }


}
