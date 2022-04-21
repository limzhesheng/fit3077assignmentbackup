package com.fit3077.assignment2.jsonEntities;

import java.util.List;

import com.fit3077.assignment2.config.CliConfig;
import org.json.*;

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

    public TestingSite() {
        this.hasOnsiteTest = CliConfig.getInstance().askQuestion("Does this site have on-site testing?");
        this.canGenerateQRCode = CliConfig.getInstance().askQuestion("Can this site generate QR Codes?");
        try {
            this.additionalInfo = new JSONObject(
                    "{\"hasOnsiteTest\":\"" + hasOnsiteTest +
                            "\",\"canGenerateQRCode\":" + canGenerateQRCode +"\"}"
            );
        }catch (JSONException err){
            this.additionalInfo = new JSONObject("{}");
        }
    }
}
