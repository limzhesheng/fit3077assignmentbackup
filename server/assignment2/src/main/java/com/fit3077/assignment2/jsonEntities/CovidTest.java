package com.fit3077.assignment2.jsonEntities;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;

public class CovidTest {
    private static final String PATIENT_KEY = "patient";
    private static final String ADMINSTERER_KEY = "administerer";
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

    public CovidTest() {/** zero-arg constructor */}

    public CovidTest(JSONObject obj) {
        ModelMapper modelMapper = new ModelMapper();
        this.patient = modelMapper.map(obj.getJSONObject(PATIENT_KEY), Patient.class);
        this.adminsterer = modelMapper.map(obj.getJSONObject(ADMINSTERER_KEY), Receptionist.class);
    }

}
