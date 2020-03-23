package com.buymypoem.springmvc.model;

public class Customer {
    private int customerID;
    private int paidcompositionnumber;
    private int userID;

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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
