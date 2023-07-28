package com.example.pfe_project.dto.holiday;

import com.example.pfe_project.models.holiday.HolidayRequest;
import com.example.pfe_project.models.training_session.EnrollmentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class HolidayRequestDto {

    @JsonProperty("holidayRequestId")
    private long holidayRequestId;
    @JsonProperty("holidayTypeId")
    private long holidayTypeId;

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("statingDate")
    private Date statingDate;

    @JsonProperty("endingDate")
    private Date endingDate;
    @JsonProperty("description")
    private String description;
    private EnrollmentStatus requestStatus;

    public HolidayRequestDto(long holidayTypeId, long userId, Date statingDay, Date endingDay, String description,EnrollmentStatus requestStatus) {
        this.holidayTypeId = holidayTypeId;
        this.userId = userId;
        this.statingDate = statingDay;
        this.endingDate = endingDay;
        this.description = description;
        this.requestStatus = requestStatus;
    }

    public HolidayRequestDto(HolidayRequest holidayRequest)
    {
        this.holidayRequestId = holidayRequest.getId();
        this.holidayTypeId = holidayRequest.getHolidayType().getId();
        this.userId = holidayRequest.getUserEntity().getId();
        this.statingDate = holidayRequest.getStartingDate();
        this.endingDate = holidayRequest.getEndingDate();
        this.description = holidayRequest.getDescription();
        this.requestStatus = holidayRequest.getRequestStatus();
    }
    public long getHolidayTypeId() {
        return holidayTypeId;
    }
    public long getUserId()
    {
        return this.userId;
    }



    public EnrollmentStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(EnrollmentStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Date getStatingDate()
    {
        return this.statingDate;
    }
    public Date getEndingDate() {
        return endingDate;
    }
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String newDescription)
    {
        this.description = newDescription;
    }
    public void setUserId(long  userId)
    {
        this.userId =userId;
    }
    public void setHolidayTypeId(long holidayTypeId)
    {
        this.holidayTypeId = holidayTypeId;
    }
    public void setStatingDate(Date statingDate)
    {
        this.statingDate = statingDate;
    }
    public void setEndingDate(Date endingDate)
    {
        this.endingDate = endingDate;
    }
}
