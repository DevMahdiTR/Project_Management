package com.example.pfe_project.dto.training_session;

public class EnrollmentRequestDTO {
    private long trainSessionId;
    private long userId;

    public long getTrainSessionId() {
        return trainSessionId;
    }

    public void setTrainSessionId(long trainSessionId) {
        this.trainSessionId = trainSessionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}