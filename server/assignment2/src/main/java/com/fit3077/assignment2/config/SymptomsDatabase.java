package com.fit3077.assignment2.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class SymptomsDatabase {
    // Access static instance of this class to get the API key (among other parameters).
    private List<String> mildSymptoms;
    private List<String> rareSymptoms;
    private List<String> severeSymptoms;
    private static SymptomsDatabase symptomsDatabase;

    private static final String JAVA_CLASS_PATH_KEY = "java.class.path";
    private static final String DS = System.getProperty("file.separator"); // file Directory Separator

    private SymptomsDatabase() {
        // void
        this.getAllSymptoms();
    }

    public static SymptomsDatabase getInstance() {
        if (symptomsDatabase == null) {
            symptomsDatabase = new SymptomsDatabase();
        }
        return symptomsDatabase;
    }

    private void getAllSymptoms(){
        String relativePath = System.getProperty(JAVA_CLASS_PATH_KEY).split(";")[0];
        try {
            // using absolute path
            String currpath = relativePath + DS + "com"+ DS +"fit3077"+ DS +"assignment2"+ DS +"config"+ DS +"db"+ DS +"mildsymptoms.txt";
            BufferedReader reader = new BufferedReader(new FileReader(currpath));
            String[] symptoms = reader.readLine().split(";");
            this.mildSymptoms = List.of(symptoms);
            reader.close();

            currpath = relativePath + DS + "com"+ DS +"fit3077"+ DS +"assignment2"+ DS +"config"+ DS +"db"+ DS +"raresymptoms.txt";
            reader = new BufferedReader(new FileReader(currpath));
            symptoms = reader.readLine().split(";");
            this.rareSymptoms = List.of(symptoms);
            reader.close();

            currpath = relativePath + DS + "com"+ DS +"fit3077"+ DS +"assignment2"+ DS +"config"+ DS +"db"+ DS +"severesymptoms.txt";
            reader = new BufferedReader(new FileReader(currpath));
            symptoms = reader.readLine().split(";");
            this.severeSymptoms = List.of(symptoms);
            reader.close();
        } catch (Exception e) {
            //
        }

    }

    public List<String> getMildSymptoms() {
        return this.mildSymptoms;
    }
    public List<String> getRareSymptoms() {
        return this.rareSymptoms;
    }
    public List<String> getSevereSymptoms() {
        return this.severeSymptoms;
    }
}
