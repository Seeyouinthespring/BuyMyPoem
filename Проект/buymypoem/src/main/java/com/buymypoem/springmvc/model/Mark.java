package com.buymypoem.springmvc.model;

public class Mark {
    private int markID;
    private int compositionID;
    private int userID;
    private boolean mark;

    public int getMarkID() {
        return markID;
    }

    public void setMarkID(int markID) {
        this.markID = markID;
    }

    public int getCompositionID() {
        return compositionID;
    }

    public void setCompositionID(int compositionID) {
        this.compositionID = compositionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
