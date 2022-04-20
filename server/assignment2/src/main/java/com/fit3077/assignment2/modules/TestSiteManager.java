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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestSiteManager {
    private static TestSiteManager testSiteManager;

    private static final HttpClient client = HttpClient.newHttpClient();
    private Scanner sc = new Scanner(System.in);

    private List<JSONObject> testSites;

    private TestSiteManager() {
        this.findAllSites();
    }
    
    public static TestSiteManager getInstance() {
        if (testSiteManager == null) {
            testSiteManager = new TestSiteManager();
        }
        return testSiteManager;
    }

    private void findAllSites() {
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

    public void displayAllSites() {
        System.out.println("Testing sites: ");
        for (int i = 0; i < testSites.size(); i++) {
            Integer displayIdx = i+1;
            System.out.println("[" + displayIdx + "]: " + testSites.get(i).getString("name"));
        }
    }

    private Boolean siteWithinRange(Integer siteIndex) {
        return siteIndex > 0 && siteIndex <= testSites.size();
    }

    public JSONObject chooseSite() {
        this.displayAllSites();
        Integer siteIndex = 0;
        while (Boolean.FALSE.equals(siteWithinRange(siteIndex))) {
            try {
                System.out.print("Select your test site: ");
                siteIndex = sc.nextInt();
                
                if (Boolean.FALSE.equals(siteWithinRange(siteIndex))) {
                    System.out.println("No site found.\n");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input.");
                sc.nextLine();
            }
        }

        return testSites.get(siteIndex-1);
    }

}
