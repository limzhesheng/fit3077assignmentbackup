package com.fit3077.assignment2.jsonEntities;

import org.json.JSONObject;

public class CovidTest {
    protected String id;
    protected String type;
    protected Patient patient;
    protected Receptionist adminsterer;
    protected String result;
    protected String status;
    protected String notes;
    protected String datePerformed; // use ISO 8601 format: YYYY-MM-DDTHH:MM:SS.SSSZ
    protected String dateOfResults; // use ISO 8601 format: YYYY-MM-DDTHH:MM:SS.SSSZ
    protected String createdAt;
    protected String updatedAt;
    protected JSONObject additionalInfo;
}
