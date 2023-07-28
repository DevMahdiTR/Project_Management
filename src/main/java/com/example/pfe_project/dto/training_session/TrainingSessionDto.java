package com.example.pfe_project.dto.training_session;

import com.example.pfe_project.models.training_session.TrainingSession;

public class TrainingSessionDto {

    private long id;
    private String name;
    private String description;

    public TrainingSessionDto(TrainingSession trainingSession)
    {
        this.id = trainingSession.getId();
        this.name = trainingSession.getName();
        this.description = trainingSession.getDescription();
    }

    public TrainingSessionDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
