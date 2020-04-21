package com.buymypoem.springmvc.model;

public class CommentRequest {
    private int commentrequestID;
    private int commentID;
    private int requestID;

    public int getCommentrequestID() {
        return commentrequestID;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }
}
