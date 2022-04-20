package com.fit3077.assignment2.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ServerConfig {
    // Access static instance of this class to get the API key (among other parameters).
    public static final String ROOT_URL = "https://fit3077.com/api/v1";
    public static final String DELIMITER = ",";
    private String apiKey;
    private static ServerConfig serverConfig;
    private static final String DS = System.getProperty("file.separator");

    private ServerConfig() throws IOException, InterruptedException{
        // void
        this.apiKey = apiKey();
    }

    public static ServerConfig getInstance() throws IOException, InterruptedException {
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
            System.out.println("[DEBUG] currpath is:\""+currpath+"\"");
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

}
