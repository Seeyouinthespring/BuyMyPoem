package com.buymypoem.springmvc.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.context.WebApplicationContext;

//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {

    private User user = new User();

    //if user is author
    private int authorID;
    private String cardNumber;
    private int finisedcompositions;
    private float rating;

    //if user is customer
    private int customerID;
    private int paidcompositionnumber;

    public void setUserSession(@Autowired User user){
        this.user = user;
    }

    public User getUserSession(){
        return user;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getFinisedcompositions() {
        return finisedcompositions;
    }

    public void setFinisedcompositions(int finisedcompositions) {
        this.finisedcompositions = finisedcompositions;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getPaidcompositionnumber() {
        return paidcompositionnumber;
    }

    public void setPaidcompositionnumber(int paidcompositionnumber) {
        this.paidcompositionnumber = paidcompositionnumber;
    }
}
