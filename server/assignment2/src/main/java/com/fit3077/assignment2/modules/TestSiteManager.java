package com.fit3077.assignment2.modules;

import java.util.Scanner;

import com.fit3077.assignment2.config.CliConfig;
import com.fit3077.assignment2.config.return_types.LoginStatusResponse;
import com.fit3077.assignment2.config.return_types.SimpleResponse;

import org.json.JSONArray;

public class TestSiteManager {
    private Scanner sc;

    private TestSiteManager(){
       this.sc = new Scanner(System.in);
    }

    public static TestSiteManager getInstance() {
        return new TestSiteManager();
    }

    public boolean askQuestion(String question) {
        Boolean searchAnswer = false;
        String searchResp = "";
        Boolean validResp = false;
        while (Boolean.FALSE.equals(validResp)) {
            System.out.print(question  + " [Y/N]");
            try {
                searchResp = sc.nextLine();
                searchAnswer = CliConfig.getInstance().yesOrNoResponse(searchResp);
                validResp = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return searchAnswer;
    }

    public boolean searchForArg(String arg) {
        return askQuestion("Search for " + arg + "?");
    }

    public void search(LoginStatusResponse userSessionToken) {
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
            System.out.println("=== Filter by facility functions ===");

            String searchDriveThruPrompt = "drive-through facilities";
            driveThru = searchForArg(searchDriveThruPrompt);
            String searchWalkInPrompt = "walk-in facilities";
            walkIn = searchForArg(searchWalkInPrompt);

            System.out.println("=== Filter by facility types ===");

            String searchClinicPrompt = "clinics";
            clinic = searchForArg(searchClinicPrompt);
            String searchGpPrompt = "GPs";
            gp = searchForArg(searchGpPrompt);
            String searchHospitalPrompt = "hospitals";
            hospital = searchForArg(searchHospitalPrompt);

            confirmSearch = askQuestion("Confirm search?");

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
                    results = new TestingSiteRequestCaller().searchTestingSite(suburbName, functions, types);
                    confirmSearch = true;
                } catch (Exception e) {
                    System.err.println(e);
                    System.err.println("Error occurred.");
                    confirmSearch = false;
                }

                if (results.statusCode() != 200) {
                    System.err.println("Error occurred.");
                    confirmSearch = false;
                }

                if (Boolean.TRUE.equals(confirmSearch)) confirmSearch = displayResults(results, userSessionToken);

            } else {
                confirmSearch = !askQuestion("Search again?");
            }


        }
    }

    public Boolean displayResults(SimpleResponse<JSONArray> results, LoginStatusResponse userSessionToken) {
        System.out.println("");
        if (results.body().length() == 0) {
            System.out.println("No results found. ");
        }
        for (int i = 0; i < results.body().length(); i++) {
            System.out.print("[" + i+1 + "]: " + results.body().get(i));
        }
        System.out.println("");

        if (results.body().length() != 0) {
            if (userSessionToken != null && userSessionToken.getLoginStatus()) {
                System.out.println("booking TBA");
            } else {
                System.out.println("You need to login before booking a test.");
            }
        }

        return !askQuestion("Search again?");

    }

}
