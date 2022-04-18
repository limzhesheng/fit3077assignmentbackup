package com.fit3077.assignment2.config.return_types;

public class UserState {
    private boolean loginStatus; 

    private String jwt;

    public UserState(boolean loginStatus, String jwt) {
        this.loginStatus = loginStatus;
        this.jwt = jwt;
    }

    public boolean getLoginStatus() {
        return this.loginStatus;
    }

    public String getJwt() {
        return this.jwt;
    }
}
