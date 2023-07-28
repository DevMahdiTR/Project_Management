package com.example.pfe_project.dto.work_position;

import com.example.pfe_project.models.work_position.WorkPosition;

public class WorkPositionDto {

    private long id ;
    private String positionName;

    private double salary;
    public WorkPositionDto(){}
    public WorkPositionDto(String positionName ,double salary) {
        this.salary = salary;
        this.positionName = positionName;
    }


    public WorkPositionDto(WorkPosition workPosition)
    {
        this.id = workPosition.getId();
        this.salary = workPosition.getSalary();
        this.positionName =workPosition.getPositionName();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
