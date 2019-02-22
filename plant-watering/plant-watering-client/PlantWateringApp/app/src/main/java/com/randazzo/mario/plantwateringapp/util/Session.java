package com.randazzo.mario.plantwateringapp.util;

public class Session {
    private static final Session ourInstance = new Session();
    private String sessionToken;

    private Session() {
    }

    public static Session getInstance() {
        return ourInstance;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void clear() {
        this.sessionToken = "";
    }

}
