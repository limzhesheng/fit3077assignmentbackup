package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.fit3077.assignment2.config.return_types.UserState;

import org.json.JSONObject;

public class LoginCli {
    private Scanner sc;
    private static LoginCli loginCli;

    private LoginCli(){
       this.sc = new Scanner(System.in);
    }

    public static LoginCli getInstance() {
        if (loginCli == null) {
            loginCli = new LoginCli();
        }
        return loginCli;
    }

    public UserState login(Integer roleCode) throws IOException, InterruptedException {
        System.out.println("\n===== Login =====");
        System.out.print("username: ");
        String username = sc.nextLine();
        System.out.print("password: ");
        String passcode = sc.nextLine();

        JSONObject loginData = new JSONObject();
        loginData.put("userName", username);
        loginData.put("password", passcode);
        
        LoginRequestCaller requestCaller = LoginRequestCaller.getInstance();
        UserState newState = null;
        try {
            HttpResponse resp = requestCaller.userLogin(loginData, roleCode, true);
            String jwt = new JSONObject(resp.body().toString()).getString("jwt");
            System.out.println("Login successful");
            newState = new UserState(true, jwt, roleCode);
        } catch (Exception e) {
            System.out.println("Login failed");
            newState = new UserState(false, "", roleCode);
        }

        return newState;
    }
    
}
