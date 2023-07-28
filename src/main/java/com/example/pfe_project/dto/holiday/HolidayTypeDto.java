package com.example.pfe_project.dto.holiday;

import com.example.pfe_project.models.holiday.HolidayType;

public class HolidayTypeDto {


    private long id;
    private String holidayName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public HolidayTypeDto(HolidayType holidayType)
    {
        this.id = holidayType.getId();
        this.holidayName = holidayType.getHolidayName();
    }
}
