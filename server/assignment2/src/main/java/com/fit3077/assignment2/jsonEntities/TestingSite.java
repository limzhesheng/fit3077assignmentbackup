package com.fit3077.assignment2.jsonEntities;

import java.util.List;

import org.json.JSONObject;

public class TestingSite {
    protected String id;
    protected String name;
    protected String description;
    protected String websiteUrl;
    protected String phoneNumber;
    protected Address address;
    protected List<Booking> bookings;
    protected String createdAt;
    protected String updatedAt;
    protected JSONObject additionalInfo;

    protected Boolean hasOnsiteTest;
    protected Boolean canGenerateQRCode;
}
