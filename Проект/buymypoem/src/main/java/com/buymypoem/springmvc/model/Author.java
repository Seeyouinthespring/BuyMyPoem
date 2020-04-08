package com.buymypoem.springmvc.model;

public class Author {
    private int authorID;
    private int finishedcompositions;
    private float rating;
    private int userID;
    private String cardNumber;

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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
