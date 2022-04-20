package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.fit3077.assignment2.config.CliConfig;
import com.fit3077.assignment2.config.return_types.UserState;
import com.fit3077.assignment2.config.return_types.SimpleResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestSiteSearchCli {
    private Scanner sc;
    private static TestSiteSearchCli testSiteSearchCli;

    private TestSiteSearchCli(){
       this.sc = new Scanner(System.in);
    }

    public static TestSiteSearchCli getInstance() {
        if (testSiteSearchCli == null) {
            testSiteSearchCli = new TestSiteSearchCli();
        }
        return new TestSiteSearchCli();
    }

    private boolean searchForArg(String arg) {
        return CliConfig.getInstance().askQuestion("Search for " + arg + "?");
    }

    public void search(UserState userSessionToken) {
        System.out.println("\n===== Search Testing Sites =====");
        Boolean confirmSearch = false;

        String suburbName = "";
        Boolean driveThru = false;
        Boolean walkIn = false;
        Boolean clinic = false;
        Boolean gp = false;
        Boolean hospital = false;

        SimpleResponse<JSONArray> results = null;

        while (Boolean.FALSE.equals(confirmSearch)) {
            System.out.print("Enter suburb name: ");
            suburbName = sc.nextLine();
            System.out.println("\n=== Filter by facility functions ===");

            String searchDriveThruPrompt = "drive-through facilities";
            driveThru = searchForArg(searchDriveThruPrompt);
            String searchWalkInPrompt = "walk-in facilities";
            walkIn = searchForArg(searchWalkInPrompt);

            System.out.println("\n=== Filter by facility types ===");

            String searchClinicPrompt = "clinics";
            clinic = searchForArg(searchClinicPrompt);
            String searchGpPrompt = "GPs";
            gp = searchForArg(searchGpPrompt);
            String searchHospitalPrompt = "hospitals";
            hospital = searchForArg(searchHospitalPrompt);

            confirmSearch = CliConfig.getInstance().askQuestion("Confirm search?");

            if (Boolean.TRUE.equals(confirmSearch)) {
                String functions = "";
                String types = "";
                if (Boolean.TRUE.equals(driveThru)) {
                    functions += "driveThru";
                }
                if (Boolean.TRUE.equals(walkIn)) {
                    if (functions.length() > 0) functions += ",";
                    functions += "walkIn";
                }
            
                if (Boolean.TRUE.equals(clinic)) {
                    types += "clinic";
                }
                if (Boolean.TRUE.equals(gp)) {
                    if (types.length() > 0) types += ",";
                    types += "gp";
                }
                if (Boolean.TRUE.equals(hospital)) {
                    if (types.length() > 0) types += ",";
                    types += "hospital";
                }
        
                try {
                    results = new TestSiteSearchRequestCaller().searchTestingSite(suburbName, functions, types);
                    confirmSearch = true;
                } catch (IOException e) {
                    System.err.println(CliConfig.ERR_PROMPT);
                    confirmSearch = false;
                } catch (InterruptedException e) {
                    System.err.println(CliConfig.ERR_PROMPT);
                    confirmSearch = false;
                    Thread.currentThread().interrupt();
                }

                if (results.statusCode() != 200) {
                    System.err.println(CliConfig.ERR_PROMPT);
                    confirmSearch = false;
                }

                if (Boolean.TRUE.equals(confirmSearch)) confirmSearch = displayResults(results, userSessionToken);

            } else {
                confirmSearch = !CliConfig.getInstance().askQuestion("Search again?");
            }

        }
    }

    private String getAddress(JSONObject testsite) {
        JSONObject address = new JSONObject(testsite.get("address").toString());
        String unitNumKey = "unitNumber";
        String streetKey = "street";
        String street2Key = "street2";
        String suburbKey = "suburb";
        String stateKey = "state";
        String postcodeKey = "postcode";
        
        String unitNum = "";
        try {
            unitNum = address.getString(unitNumKey);
        } catch (JSONException e) { /** */}

        String street = "";
        try {
            street = address.getString(streetKey);
        } catch (JSONException e) { /** */}

        String street2 = "";
        try {
            street2 = address.getString(street2Key);
        } catch (JSONException e) { /** */}

        String combinedStreet = street + (street2.strip().length() != 0 ? " " + street2 : "");

        String suburb = "";
        try {
            suburb = address.getString(suburbKey);
        } catch (JSONException e) { /** */}

        String state = "";
        try {
            state = address.getString(stateKey);
        } catch (JSONException e) { /** */}

        String postcode = "";
        try {
            postcode = address.getString(postcodeKey);
        } catch (JSONException e) { /** */}

        return unitNum + " " + combinedStreet + ", " + suburb + " " + state + " " + postcode;
    }

    private Boolean displayResults(SimpleResponse<JSONArray> results, UserState userSessionToken) {
        System.out.println("");
        if (results.body().length() == 0) {
            System.out.println("No results found. ");
        }
        for (int i = 0; i < results.body().length(); i++) {
            Integer idx = i+1;
            JSONObject testsite = new JSONObject(results.body().get(i).toString());
            System.out.println("[" + idx + "]: " + testsite.getString("name"));
            System.out.println("Address: " + getAddress(testsite));
        }
        System.out.println("");

        if (results.body().length() != 0) {
            if (userSessionToken != null && userSessionToken.getLoginStatus()) {
                Boolean testConfirm = false;
                Integer testSiteChosen = -1;

                while (!testConfirm) {
                    try {
                        System.out.print("Select test site: ");
                        testSiteChosen = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.err.println("Invalid input.");
                        sc.nextLine();
                    }
                    Integer idx2 = testSiteChosen-1;

                    if (idx2 >= 0 && idx2 < results.body().length()) {
                        JSONObject testsite = new JSONObject(results.body().get(idx2).toString());
                        System.out.println("[" + idx2+1 + "]: " + testsite.getString("name"));
                        System.out.println("Address: " + getAddress(testsite));
                    }

                    testConfirm = CliConfig.getInstance().askQuestion("Confirm test?");
                }
            } else {
                System.out.println("You need to login before booking a test.");
            }
        }

        return !CliConfig.getInstance().askQuestion("Search again?");

    }

}
