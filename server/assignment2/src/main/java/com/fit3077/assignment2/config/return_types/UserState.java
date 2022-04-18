package com.fit3077.assignment2.config.return_types;

import java.util.HashMap;
import java.util.Map;

public class UserState {
    private boolean loginStatus;

    private String jwt;

    private Integer roleCode;

    public static Map<Integer, String> getRoles() {
        Map<Integer, String> returnList = new HashMap<>();
        returnList.put(0, "");
        returnList.put(1, "isCustomer");
        returnList.put(3, "isReceptionist");
        returnList.put(4, "isHealthcareWorker");
        return returnList;
    }

    public UserState(boolean loginStatus, String jwt, Integer roleCode) {
        this.loginStatus = loginStatus;
        this.jwt = jwt;
        this.roleCode = roleCode;
    }

    public boolean getLoginStatus() {
        return this.loginStatus;
    }

    public String getJwt() {
        return this.jwt;
    }

    public Integer getRoleCode() {
        return this.roleCode;
    }
}
