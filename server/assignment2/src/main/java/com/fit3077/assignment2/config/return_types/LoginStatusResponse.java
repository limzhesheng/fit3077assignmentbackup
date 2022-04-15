package com.fit3077.assignment2.config.return_types;

public class LoginStatusResponse {
    private boolean loginStatus; 

    private String jwt;

    public LoginStatusResponse(boolean loginStatus, String jwt) {
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
