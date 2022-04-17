package com.fit3077.assignment2.config;

public class CliConfig {
    // Access static instance of this class to get the CLI parameters.

    private CliConfig(){
        // void
    }

    public static CliConfig getInstance() {
        return new CliConfig();
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
