package com.buymypoem.springmvc.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public class Comment {
    private int commentID;
    private String text;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sendingDate;
    private User user;

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

