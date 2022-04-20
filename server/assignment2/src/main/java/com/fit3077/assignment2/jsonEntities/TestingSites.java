package com.fit3077.assignment2.jsonEntities;

import org.json.JSONObject;

public class TestingSites {
    protected String id;
    protected String name;
    protected String description;
    protected String websiteUrl;
    protected String phoneNumber;
    protected Addresses address;
    protected Bookings bookings;
    protected String createdAt;
    protected String updatedAt;
    protected JSONObject additionalInfo;

    protected Boolean hasOnsiteTest;
    protected Boolean canGenerateQRCode;
}
