package com.buymypoem.springmvc.model;

public class Author extends User {
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

    @Override
    public int getUserID() {
        return userID;
    }

    @Override
    public void setUserID(int userID) {
        this.userID = userID;
    }
}
