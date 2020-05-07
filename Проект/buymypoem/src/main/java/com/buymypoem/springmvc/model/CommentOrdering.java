package com.buymypoem.springmvc.model;

public class CommentOrdering {
    private int commentorderingID;
    private int commentID;
    private int orderingID;

    public int getCommentorderingID() {
        return commentorderingID;
    }

    public void setCommentorderingID(int commentorderingID) {
        this.commentorderingID = commentorderingID;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getOrderingID() {
        return orderingID;
    }

    public void setOrderingID(int orderingID) {
        this.orderingID = orderingID;
    }
}
