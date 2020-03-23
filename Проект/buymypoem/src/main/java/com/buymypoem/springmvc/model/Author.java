package com.buymypoem.springmvc.model;

public class Author {
    private int authorID;
    private int finishedcompositions;
    private int rating;
    private int userID;

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public int getFinishedcompositions() {
        return finishedcompositions;
    }

    public void setFinishedcompositions(int finishedcompositions) {
        this.finishedcompositions = finishedcompositions;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
