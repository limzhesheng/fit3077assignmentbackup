package com.fit3077.assignment2.modules;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.fit3077.assignment2.config.ServerConfig;
import com.fit3077.assignment2.modules.interfaces.MutableJsonStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestSiteManager implements MutableJsonStorage {
    private static TestSiteManager testSiteManager;

    private static final HttpClient client = HttpClient.newHttpClient();
    private Scanner sc = new Scanner(System.in);

    private static final String BOOKINGS_KEY = "bookings";
    private static final String CUSTOMER_KEY = "customer";

    private List<JSONObject> testSites;

    private TestSiteManager() {
        this.refresh();
    }
    
    public static TestSiteManager getInstance() {
        if (testSiteManager == null) {
            testSiteManager = new TestSiteManager();
        }
        return testSiteManager;
    }

    public void refresh() {
        testSites = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest
            .newBuilder(URI.create(ServerConfig.TESTING_SITE_URL))
            .setHeader(ServerConfig.AUTH_HEADER_KEY, ServerConfig.getInstance().getApiKey())
            .GET()
            .build();

            HttpResponse resp = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray testSiteArray = new JSONArray(resp.body().toString());

            for (int i = 0; i < testSiteArray.length(); i++) {
                try {
                    JSONObject currObj = testSiteArray.getJSONObject(i);
                    testSites.add(currObj);
                } catch (JSONException e) {
                    // continue
                }
                
            }
        } catch (Exception e) {
            System.err.println("Warning: test site data not cached");
        }
    }

    private void displayAllSites() {
        System.out.println("Testing sites: ");
        for (int i = 0; i < testSites.size(); i++) {
            Integer displayIdx = i+1;
            System.out.println("[" + displayIdx + "]: " + testSites.get(i).getString("name"));
        }
    }

    private Boolean withinRange(Integer idx, JSONArray list) {
        return idx > 0 && idx <= list.length();
    }

    private Boolean withinRange(Integer idx, List list) {
        return idx > 0 && idx <= list.size();
    }

    public JSONObject chooseSite() {
        this.displayAllSites();
        Integer siteIndex = 0;
        while (Boolean.FALSE.equals(withinRange(siteIndex, testSites))) {
            try {
                System.out.print("Select your test site: ");
                siteIndex = sc.nextInt();
                
                if (Boolean.FALSE.equals(withinRange(siteIndex, testSites))) {
                    System.out.println("No site found.\n");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input.");
                sc.nextLine();
            }
        }

        return testSites.get(siteIndex-1);
    }

    private void displayAllBookings(JSONObject testsite) {
        JSONArray bookings = testsite.getJSONArray(BOOKINGS_KEY);

        System.out.println("Bookings for this site: ");
        for (int i = 0; i < bookings.length(); i++) {
            Integer displayIdx = i+1;
            JSONObject currBooking = bookings.getJSONObject(i);
            JSONObject currCustomer = currBooking.getJSONObject(CUSTOMER_KEY);

            System.out.println("[" + displayIdx + "]: " + currBooking.getString("id"));
            System.out.println(currCustomer.getString(ServerConfig.GIVEN_NAME_KEY) + " " + currCustomer.getString(ServerConfig.FAMILY_NAME_KEY));
        }
    }

    public JSONObject chooseCovidBooking(JSONObject testsite) {
        this.displayAllBookings(testsite);
        Integer bookingIndex = 0;
        JSONArray bookings = testsite.getJSONArray(BOOKINGS_KEY);
        while (Boolean.FALSE.equals(withinRange(bookingIndex, bookings))) {
            try {
                System.out.print("Select your booking: ");
                bookingIndex = sc.nextInt();
                
                if (Boolean.FALSE.equals(withinRange(bookingIndex, bookings))) {
                    System.out.println("No booking found.\n");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input.");
                sc.nextLine();
            }
        }

        return bookings.getJSONObject(bookingIndex-1);
    }

}
