package com.example.pfe_project.dto.feed_back;

import com.example.pfe_project.models.feedBack.FeedBackUser;

public class FeedBackUserDto {


    private long userId ,commentId,feedBackId;
    private String comment;

    public FeedBackUserDto(FeedBackUser feedBackUser) {
        this.commentId = feedBackUser.getId();
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

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
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
