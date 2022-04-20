package com.fit3077.assignment2.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ServerConfig {
    // Access static instance of this class to get the API key (among other parameters).
    public static final String ROOT_URL = "https://fit3077.com/api/v1";

    public static final String USER_URL = ROOT_URL + "/user";
    public static final String TESTING_SITE_URL = ROOT_URL + "/testing-site";

    public static final String DELIMITER = ",";
    private String apiKey;
    private static ServerConfig serverConfig;
    private static final String DS = System.getProperty("file.separator"); // file Directory Separator

    public static final String AUTH_HEADER_KEY = "Authorization";

    public static final String USERNAME_KEY = "userName";
    public static final String GIVEN_NAME_KEY = "givenName";
    public static final String FAMILY_NAME_KEY = "familyName";
    public static final String ADDITIONAL_INFO_KEY = "additionalInfo";
    public static final String IS_HOME_TEST_KEY = "isHomeTestBooking";

    private ServerConfig(){
        // void
        this.apiKey = apiKey();
    }

    public static ServerConfig getInstance() {
        if (serverConfig == null) {
            serverConfig = new ServerConfig();
        }
        return serverConfig;
    }

    private String apiKey(){
        String key = "";
        try {
            // using absolute path
            String currpath = System.getProperty("java.class.path").split(";")[0] + DS + "com"+DS+"fit3077"+DS+"assignment2"+DS+"config"+DS+"res"+DS+"api.txt";
            BufferedReader reader = new BufferedReader(new FileReader(currpath));
            key = reader.readLine();
            reader.close();
            return key;
        } catch (Exception e) {
            return key;
        }

    }

    public String getApiKey() {
        return this.apiKey;
    }

    public String instantToIsoString(Instant date) {
        return date.truncatedTo(ChronoUnit.MILLIS).toString();
    }

}
