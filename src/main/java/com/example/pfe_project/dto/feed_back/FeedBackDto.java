package com.example.pfe_project.dto.feed_back;


import com.example.pfe_project.models.feedBack.FeedBack;

public class FeedBackDto {
    private long id;

    private String feedBackName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FeedBackDto(long id, String feedBackName) {
        this.id = id;
        this.feedBackName = feedBackName;
    }

    public FeedBackDto(String feedBackName) {
        this.feedBackName = feedBackName;
    }

    public FeedBackDto(FeedBack feedBack)
    {
        this.id = feedBack.getId();
        this.feedBackName = feedBack.getFeedBackName();
    }
    public String getFeedBackName() {
        return feedBackName;
    }

    public void setFeedBackName(String feedBackName) {
        this.feedBackName = feedBackName;
    }
}
