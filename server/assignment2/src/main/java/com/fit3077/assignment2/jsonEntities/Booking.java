package com.fit3077.assignment2.jsonEntities;

import java.util.List;

import org.json.JSONObject;

public class Booking {
    protected String id;
    protected Patient customer;
    protected String createdAt;
    protected String updatedAt;
    protected String startTime;
    protected String smsPin;
    protected String status;
    protected List<CovidTest> covidTests;
    protected String notes;
    protected JSONObject additionalInfo;
}
