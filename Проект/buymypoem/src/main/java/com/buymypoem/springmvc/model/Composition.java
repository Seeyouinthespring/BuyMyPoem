package com.buymypoem.springmvc.model;

public class Composition {
    private int CompositionID;
    private String Title;
    private int Likes;
    private int Dislikes;
    private String Text;
    private User user;
    private Genre genre;
    private Type type;
    private String Status;
    private String description;
    private int ownerID;

    public int getCompositionID() {
        return CompositionID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getLikes() {
        return Likes;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public int getDislikes() {
        return Dislikes;
    }

    public void setDislikes(int dislikes) {
        Dislikes = dislikes;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setCompositionID(int compositionID) {
        CompositionID = compositionID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
}