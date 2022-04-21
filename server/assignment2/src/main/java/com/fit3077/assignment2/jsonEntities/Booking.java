package com.fit3077.assignment2.jsonEntities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;

public class Booking {
    private static final String CUSTOMER_KEY = "customer";
    private static final String COVID_TESTS_KEY = "covidTests";
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

    public Booking() {/** zero-arg constructor */}

    public Booking(JSONObject obj) {
        ModelMapper modelMapper = new ModelMapper();
        this.customer = modelMapper.map(obj.getJSONObject(CUSTOMER_KEY), Patient.class);
        JSONArray covidTestsRawData = obj.getJSONArray(COVID_TESTS_KEY);
        for (int i = 0; i < covidTestsRawData.length(); i++) {
            this.addCovidTest(new CovidTest(covidTestsRawData.getJSONObject(i)));
        }
    }

    private void addCovidTest(CovidTest c) {
        if (this.covidTests == null) {
            this.covidTests = new ArrayList<>();
        }
        this.covidTests.add(c);
    }

    /**
     * Booking nested items:
     * Booking --|
     *           |--> Patient
     *           |--> List<CovidTest> --|
     *                                  |--> Patient
     *                                  |--> Receptionist
     */
}
