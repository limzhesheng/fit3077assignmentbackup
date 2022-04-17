package com.fit3077.assignment2.config;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.tomcat.jni.File;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    // Access static instance of this class to get the API key (among other parameters).
    public static final String ROOT_URL = "https://fit3077.com/api/v1";
    public static final String DELIMITER = ",";

    public ServerConfig(){
        // void
    }

    public static String apiKey(){
        String key = "";
        try {
            // using absolute path
            String currpath = System.getProperty("java.class.path").split(";")[0] + "/com/fit3077/assignment2/config/res/api.txt";
            BufferedReader reader = new BufferedReader(new FileReader(currpath));
            key = reader.readLine();
            reader.close();
            return key;
        } catch (Exception e) {
            return key;
        }

    }

}
