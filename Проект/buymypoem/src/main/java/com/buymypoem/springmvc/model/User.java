package com.buymypoem.springmvc.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User {
    private int UserID;
    private String Login;
    private String Password;
    private String Email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date Birthdate;
    private String About;
    private Date Registredate;
    private String confirmPassword;
    private String role;
    private String photo;
    private int numb_composition;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Date getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(Date birthdate) {
        Birthdate = birthdate;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public Date getRegistredate() {
        return Registredate;
    }

    public void setRegistredate(Date registredate) {
        Registredate = registredate;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getNumb_composition() {
        return numb_composition;
    }

    public void setNumb_composition(int numb_composition) {
        this.numb_composition = numb_composition;
    }
}
