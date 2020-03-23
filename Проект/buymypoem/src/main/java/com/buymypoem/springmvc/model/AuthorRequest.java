package com.buymypoem.springmvc.model;

public class AuthorRequest {
    private int authorrequestID;
    private int authorID;
    private int requestID;

    public int getAuthorrequestID() {
        return authorrequestID;
    }

    public void setAuthorrequestID(int authorrequestID) {
        this.authorrequestID = authorrequestID;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }
}
