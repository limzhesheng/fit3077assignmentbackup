package com.fit3077.assignment2.config;

public class CliConfig {
    // Access static instance of this class to get the CLI parameters.

    private CliConfig(){
        // void
    }

    public static CliConfig getInstance() {
        return new CliConfig();
    }

}
