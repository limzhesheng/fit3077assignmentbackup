package com.fit3077.assignment2.modules;

import java.net.http.HttpResponse;
import java.util.Scanner;

import com.fit3077.assignment2.config.return_types.UserState;

import org.json.JSONObject;

public class LoginCli {
    private Scanner sc;

    private LoginCli(){
       this.sc = new Scanner(System.in);
    }

    public static LoginCli getInstance() {
        return new LoginCli();
    }

    public UserState login() {
        System.out.println("\n===== Login =====");
        System.out.print("username: ");
        String username = sc.nextLine();
        System.out.print("password: ");
        String passcode = sc.nextLine();

        JSONObject loginData = new JSONObject();
        loginData.put("userName", username);
        loginData.put("password", passcode);
        
        LoginRequestCaller requestCaller = new LoginRequestCaller();
        try {
            HttpResponse resp = requestCaller.userLogin(loginData, true);
            String jwt = new JSONObject(resp.body().toString()).getString("jwt");
            System.out.println("Login successful");
            return new UserState(true, jwt);
        } catch (Exception e) {
            System.err.println(e);
            System.out.println("Login failed");
            return new UserState(false, "");
        }
    }
    
}
