package com.example.pfe_project.dto.holiday;

public class HolidayRequestHandlerDto {

    private long userId;
    private long holidayTypeId;


    public  long getUserId()
    {
        return this.userId;
    }

    public long getHolidayTypeId()
    {
        return this.holidayTypeId;
    }
}
