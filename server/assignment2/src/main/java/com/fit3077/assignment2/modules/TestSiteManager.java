package com.fit3077.assignment2.modules;

import java.util.Scanner;

public class TestSiteManager {
    private Scanner sc;

    private TestSiteManager(){
       this.sc = new Scanner(System.in);
    }

    public static TestSiteManager getInstance() {
        return new TestSiteManager();
    }

    public void search() {
        // 
    }
}
