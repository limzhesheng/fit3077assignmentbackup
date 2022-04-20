package com.fit3077.assignment2.jsonEntities;

import org.json.JSONObject;

public class Bookings {
    protected String id;
    protected Patients customer;
    protected String createdAt;
    protected String updatedAt;
    protected String startTime;
    protected String smsPin;
    protected String status;
    protected CovidTests covidTest;
    protected String notes;
    protected JSONObject additionalInfo;
}
