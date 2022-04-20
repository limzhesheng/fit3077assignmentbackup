package com.fit3077.assignment2.jsonEntities;

import org.json.JSONObject;

public class User {
    protected String id;
    protected String givenName;
    protected String familyName;
    protected String userName;
    protected String phoneNumber;
    protected Boolean isCustomer;
    protected Boolean isReceptionist; // isAdmin does not exist in actual JSON HTTP response
    protected Boolean isHealthcareWorker;
    protected JSONObject additionalInfo;
}
