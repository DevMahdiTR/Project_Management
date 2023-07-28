package com.example.pfe_project.dto.feed_back;

import com.example.pfe_project.models.feedBack.FeedBackUser;

public class FeedBackUserRequestDto {


    private long userId ,feedBackId;
    private String comment;

    public FeedBackUserRequestDto(long userId, long feedBackId, String comment) {
        this.userId = userId;
        this.feedBackId = feedBackId;
        this.comment = comment;
    }

    public FeedBackUserRequestDto(FeedBackUser feedBackUser)
    {
        this.userId = feedBackUser.getUserEntity().getId();
        this.feedBackId = feedBackUser.getFeedBack().getId();
        this.comment = feedBackUser.getComment();
    }
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(long feedBackId) {
        this.feedBackId = feedBackId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



}
