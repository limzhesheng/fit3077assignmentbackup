package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.util.Scanner;

import com.fit3077.assignment2.config.CliConfig;
import com.fit3077.assignment2.config.return_types.UserState;
import com.fit3077.assignment2.config.return_types.SimpleResponse;

import org.json.JSONArray;

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
                    System.err.println("Error occurred.");
                    confirmSearch = false;
                } catch (InterruptedException e) {
                    System.err.println("Error occurred.");
                    confirmSearch = false;
                    Thread.currentThread().interrupt();
                }

                if (results.statusCode() != 200) {
                    System.err.println("Error occurred.");
                    confirmSearch = false;
                }

                if (Boolean.TRUE.equals(confirmSearch)) confirmSearch = displayResults(results, userSessionToken);

            } else {
                confirmSearch = !CliConfig.getInstance().askQuestion("Search again?");
            }

        }
    }

    private Boolean displayResults(SimpleResponse<JSONArray> results, UserState userSessionToken) {
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

        return !CliConfig.getInstance().askQuestion("Search again?");

    }

}
