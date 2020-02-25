package com.buymypoem.springmvc.model;

public class Composition {
    private int CompositionID;
    private String Title;
    private int Likes;
    private int Dislikes;
    private String Text;
    private int AuthorID;
    private int GenreID;
    private int TypeID;
    private String Status;

    public int getCompositionID() {
        return CompositionID;
    }

    public int getAuthorID() {
        return AuthorID;
    }

    public void setAuthorID(int authorID) {
        AuthorID = authorID;
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

    public int getGenreID() {
        return GenreID;
    }

    public void setGenreID(int genreID) {
        GenreID = genreID;
    }

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int typeID) {
        TypeID = typeID;
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
}