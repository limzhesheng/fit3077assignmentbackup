package com.fit3077.assignment2.modules;

import java.time.Instant;
import java.util.Scanner;

import com.fit3077.assignment2.config.CliConfig;
import com.fit3077.assignment2.config.ServerConfig;
import com.fit3077.assignment2.config.return_types.UserState;

import com.fit3077.assignment2.obervers.ConcreteWatched;
import com.fit3077.assignment2.obervers.ConcreteWatcher;
import com.fit3077.assignment2.obervers.Watcher;
import org.json.JSONObject;

public class TestBookingCli extends ConcreteWatched {
    private Scanner sc;
    private static TestBookingCli testBookingCli;
    private static Watcher newInstanceWatcher;
    private static final String NOTES_KEY = "notes";
    private static final String START_TIME_KEY = "startTime";

    private TestBookingCli(){
       this.sc = new Scanner(System.in);
    }

    public static TestBookingCli getInstance() {
        if (testBookingCli == null) {
            testBookingCli = new TestBookingCli();
            if (newInstanceWatcher == null) {
                // Clear all previous watchers
                testBookingCli.clearAllWatchers();
                // Create a newInstanceWatcher
                newInstanceWatcher = new ConcreteWatcher();
                // Add watchers to this loginCli
                testBookingCli.addWatcher(newInstanceWatcher);
                // Notify observer that a new instance of LoginCli has been instantiated
                testBookingCli.notifyWatchers("A new Instance of Test Booking CLI has been created");
            }
        }
        return testBookingCli;
    }

    private JSONObject makeBookingRequestBody(String givenName, String familyName, String notes, Boolean isHomeTestBooking) {
        JSONObject body = new JSONObject();
        body.put(ServerConfig.GIVEN_NAME_KEY, givenName);
        body.put(ServerConfig.FAMILY_NAME_KEY, familyName);
        body.put(START_TIME_KEY, ServerConfig.getInstance().instantToIsoString(Instant.now()));
        body.put(NOTES_KEY, notes);

        JSONObject additionalInfo = new JSONObject();
        additionalInfo.put(ServerConfig.IS_HOME_TEST_KEY, isHomeTestBooking);
        body.put(ServerConfig.ADDITIONAL_INFO_KEY, additionalInfo);
        return body;
    }

    /**TODO Confirm details required */
    public void bookTest(JSONObject testsite, UserState userSessionToken, Boolean isOnSite) {
        Boolean abortBooking = false;
        Boolean completeBooking = false;
        System.out.println("");
        System.out.println("===== Booking test =====\n");

        JSONObject patient = null;

        String givenName = "";
        String familyName = "";
        String notes = "";
        Boolean isHomeTestBooking = false;

        while (Boolean.FALSE.equals(completeBooking)) {
            while (patient == null) {
                System.out.println("===== Patient details =====");
                System.out.print("Given name: ");
                givenName = sc.nextLine();
                System.out.print("Family name: ");
                familyName = sc.nextLine();
        
                patient = UserManager.getInstance().getUserByFullName(givenName, familyName);
                if (patient == null) {
                    System.out.println("No patient found. ");
                    abortBooking = CliConfig.getInstance().askQuestion("Cancel booking?");
                    if (Boolean.TRUE.equals(abortBooking)) {
                        return;
                    }
                }
            }
    
            System.out.print("\nTest notes: ");
            notes = sc.nextLine();
            if (Boolean.TRUE.equals(isOnSite)) {
                isHomeTestBooking = CliConfig.getInstance().askQuestion("Are you requesting for home testing?");
            }

            completeBooking = CliConfig.getInstance().askQuestion("Submit booking?");
            if (Boolean.FALSE.equals(completeBooking)) {
                abortBooking = CliConfig.getInstance().askQuestion("Cancel booking?");
                if (Boolean.TRUE.equals(abortBooking)) {
                    return;
                }
            }
        }

        JSONObject requestBody = this.makeBookingRequestBody(givenName, familyName, notes, isHomeTestBooking);
        System.out.println(requestBody);

    }

    public void bookTestViaSystem(JSONObject testsite, UserState userSessionToken) {
        bookTest(testsite, userSessionToken, false);
    }

    public void bookTestOnSite(JSONObject testsite, UserState userSessionToken) {
        bookTest(testsite, userSessionToken, true);
    }
}
