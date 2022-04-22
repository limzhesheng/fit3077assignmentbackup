package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.fit3077.assignment2.config.CliConfig;
import com.fit3077.assignment2.config.return_types.UserState;

import com.fit3077.assignment2.obervers.ConcreteWatched;
import com.fit3077.assignment2.obervers.ConcreteWatcher;
import com.fit3077.assignment2.obervers.Watched;
import com.fit3077.assignment2.obervers.Watcher;
import org.json.JSONObject;

public class LoginCli extends ConcreteWatched {
    private Scanner sc;
    private static LoginCli loginCli;
    private static Watcher newInstanceWatcher;

    private LoginCli(){
       this.sc = new Scanner(System.in);
    }

    public static LoginCli getInstance() {
        if (loginCli == null) {
            loginCli = new LoginCli();

            if (newInstanceWatcher == null) {
                // Clear all previous watchers
                loginCli.clearAllWatchers();
                // Create a newInstanceWatcher
                newInstanceWatcher = new ConcreteWatcher();
                // Add watchers to this loginCli
                loginCli.addWatcher(newInstanceWatcher);
                // Notify observer that a new instance of LoginCli has been instantiated
                loginCli.notifyWatchers("A new Instance of Login CLI has been created");
            }
        }
        return loginCli;
    }

    public UserState login(Integer roleCode) throws IOException, InterruptedException {
        UserState newState = null;
        LoginRequestCaller requestCaller = LoginRequestCaller.getInstance();
        Boolean loginDone = false;

        while (Boolean.FALSE.equals(loginDone)) {
            System.out.println("\n===== Login =====");
            System.out.print("username: ");
            String username = sc.nextLine();
            System.out.print("password: ");
            String passcode = sc.nextLine();

            JSONObject loginData = new JSONObject();
            loginData.put("userName", username);
            loginData.put("password", passcode);
            
            try {
                HttpResponse resp = requestCaller.userLogin(loginData, roleCode, true);
                String jwt = new JSONObject(resp.body().toString()).getString("jwt");
                System.out.println("Login successful");
                newState = new UserState(true, jwt, roleCode);
                loginDone = true;
            } catch (Exception e) {
                System.out.println("Login failed");
                newState = new UserState(false, "", roleCode);
                loginDone = !CliConfig.getInstance().askQuestion("Try again?");
            }
        }


        return newState;
    }
    
}
