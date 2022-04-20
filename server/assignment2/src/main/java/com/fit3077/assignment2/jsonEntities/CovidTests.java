package com.fit3077.assignment2.jsonEntities;

import org.json.JSONObject;

public class CovidTests {
    protected String id;
    protected String type;
    protected Patients patient;
    protected Receptionists receptionist;
    protected String result;
    protected String status;
    protected String notes;
    protected String datePerformed; // use ISO 8601 format: YYYY-MM-DDTHH:MM:SS.SSSZ
    protected String dateOfResults; // use ISO 8601 format: YYYY-MM-DDTHH:MM:SS.SSSZ
    protected String createdAt;
    protected String updatedAt;
    protected JSONObject additionalInfo;
}
