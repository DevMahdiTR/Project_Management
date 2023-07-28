package com.example.pfe_project.dto.holiday;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HolidayTypeUpdateRequestDto {
    @JsonProperty("holiday_name")
    private String newHolidayName;

    public String getNewHolidayName()
    {
        return this.newHolidayName;
    }

    public void setNewHolidayName(String newHolidayName)
    {
        this.newHolidayName = newHolidayName;
    }
}
