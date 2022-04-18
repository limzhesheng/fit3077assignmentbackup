package com.fit3077.assignment2.config;

import java.util.Scanner;

public class CliConfig {
    // Access static instance of this class to get the CLI parameters.
    private static CliConfig cliConfig;
    private Scanner sc = new Scanner(System.in);

    private CliConfig(){
        // void
    }

    public static CliConfig getInstance() {
        if (cliConfig == null) {
            cliConfig = new CliConfig();
        }
        return cliConfig;
    }

    public boolean askQuestion(String question) {
        Boolean answer = false;
        String resp = "";
        Boolean validResp = false;
        while (Boolean.FALSE.equals(validResp)) {
            System.out.print(question  + " [Y/N]");
            try {
                resp = sc.nextLine();
                answer = yesOrNoResponse(resp);
                validResp = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return answer;
    }

    public boolean yesOrNoResponse(String answer) {
        if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
            return true;
        } else if (answer.equalsIgnoreCase("N") || answer.equalsIgnoreCase("NO")) {
            return false;
        } else {
            throw new IllegalArgumentException("Answer must be one of 'Y' or 'N'");
        }
    }

}
