package com.fit3077.assignment2.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import com.fit3077.assignment2.config.CliConfig;
import com.fit3077.assignment2.config.SymptomsDatabase;

public class OnSiteTestCli {
    private static Scanner sc = new Scanner(System.in);
	private static OnSiteTestCli onSiteTestCli;

    private OnSiteTestCli() {/*..*/}

    public static OnSiteTestCli getInstance() {
        if (onSiteTestCli == null) {
            onSiteTestCli = new OnSiteTestCli();
        }
        return onSiteTestCli;
    }

    private Map<Integer, String> contactDegree() {
        Map<Integer, String> returnList = new HashMap<>();
        returnList.put(1, "Patient has no contact with suspected or confirmed COVID-19 cases");
        returnList.put(2, "Patient is casual contact with suspected COVID-19 cases");
        returnList.put(3, "Patient is close contact with confirmed COVID-19 cases");
        returnList.put(4, "Patient is casual contact with confirmed COVID-19 cases");
        returnList.put(5, "Patient is close contact with confirmed COVID-19 cases");
        return returnList;
    }

    private Boolean validContactDegreeInput(Integer i) {
        return contactDegree().get(i) != null;
    }

    private Integer hasSymptom(String symptom) {
        Boolean resp = CliConfig.getInstance().askQuestion(symptom + "?");
        return Boolean.TRUE.equals(resp) ? 1 : 0;
    }

    /**TODO Confirm requirements */
    public void onSiteTestForm() {
        System.out.println("===== On-site Testing Form =====");

        Boolean confirmSubmit = false;

        Integer contactDegree = 0;
        Integer mildSymptomCount = 0;
        Integer rareSymptomCount = 0;
        Integer severeSymptomCount = 0;

        while (Boolean.FALSE.equals(confirmSubmit)) {
            while (Boolean.FALSE.equals(validContactDegreeInput(contactDegree))) {
                System.out.println("Over the last 14 days... (choose one)");
                for (Entry<Integer, String> entry : contactDegree().entrySet()) {
                    System.out.println("[" + entry.getKey() + "] " + entry.getValue());
                }

                contactDegree = sc.nextInt();
                if (Boolean.FALSE.equals(validContactDegreeInput(contactDegree))) {
                    System.err.println("Invalid input");
                }
            }

            List<String> mildSymptoms = SymptomsDatabase.getInstance().getMildSymptoms();
            List<String> rareSymptoms = SymptomsDatabase.getInstance().getRareSymptoms();
            List<String> severeSymptoms = SymptomsDatabase.getInstance().getSevereSymptoms();

            System.out.println("\nDoes patient have the symptoms listed below: ");
            for (int i = 0; i < mildSymptoms.size(); i++) {
                mildSymptomCount = mildSymptomCount + hasSymptom(mildSymptoms.get(i));
            }
            for (int i = 0; i < rareSymptoms.size(); i++) {
                rareSymptomCount = rareSymptomCount + hasSymptom(rareSymptoms.get(i));
            }
            for (int i = 0; i < severeSymptoms.size(); i++) {
                severeSymptomCount = severeSymptomCount + hasSymptom(severeSymptoms.get(i));
            }
            confirmSubmit = CliConfig.getInstance().askQuestion("Submit form?");
        }
    }

}
