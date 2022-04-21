package com.fit3077.assignment2.jsonEntities;

import java.util.ArrayList;
import java.util.List;

import com.fit3077.assignment2.config.CliConfig;
import org.json.*;
import org.modelmapper.ModelMapper;

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

    private static final String ADDRESS_KEY = "address";
    private static final String BOOKINGS_KEY = "bookings";

    public TestingSite(JSONObject obj) {
        ModelMapper modelMapper = new ModelMapper();
        this.address = modelMapper.map(obj.getJSONObject(ADDRESS_KEY), Address.class);
        JSONArray bookingsRawData = obj.getJSONArray(BOOKINGS_KEY);
        for (int i = 0; i < bookingsRawData.length(); i++) {
            this.addBooking(new Booking(bookingsRawData.getJSONObject(i)));
        }
    }

    public void addBooking(Booking b) {
        if (this.bookings == null) {
            this.bookings = new ArrayList<>();
        }
        this.bookings.add(b);
    }

    /**
     * TestingSite nested items:
     * TestingSite --|
     *               |--> Address
     *               |--> List<Booking> --|
     *                                    |--> Patient
     *                                    |--> List<CovidTest> --|
     *                                                           |--> Patient
     *                                                           |--> Receptionist
     * 
     * When using ModelMapper you may need to map JSON to each of the classes from bottom up.
     * For example:
     * for (Object b : testsite.getJSONArray("bookings")) {
     *     JSONObject bk = new JSONObject(b);
     *     Patient p1 = new ModelMapper().map(bk.getJSONObject("patient"), Patient.class);
     *     for (Object c : new JSONObject(b).getJSONArray("covidTests")) {
     *         JSONObject ct = new JSONObject(c);
     *         Patient p2 = new ModelMapper().map(ct.getJSONObject("patient"), Patient.class);
     *         Receptionist r = new ModelMapper().map(ct.getJSONObject("receptionist"), Receptionist.class);
     *         // create CovidTest object with p2 and r, and put into array
     *     }
     *     // create Booking object with p1, and put into array
     * }
     */

}
