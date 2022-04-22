package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fit3077.assignment2.config.ServerConfig;
import com.fit3077.assignment2.config.return_types.UserState;

import com.fit3077.assignment2.obervers.ConcreteWatched;
import com.fit3077.assignment2.obervers.ConcreteWatcher;
import com.fit3077.assignment2.obervers.Watcher;
import org.json.JSONObject;

public class LoginRequestCaller extends ConcreteWatched {
    // API calls here. Most methods here are really just man-in-the-middle methods that assist the frontend in communicating with the server.
    private static LoginRequestCaller loginRequestCaller;
    private static Watcher newInstanceWatcher;
    private static final HttpClient client = HttpClient.newHttpClient();

    private static final String USERNAME_KEY = "userName";

    private LoginRequestCaller() {
        UserManager.getInstance();
    }
    
    public static LoginRequestCaller getInstance() {
        if (loginRequestCaller == null) {
            loginRequestCaller = new LoginRequestCaller();
            if (newInstanceWatcher == null) {
                // Clear all previous watchers
                loginRequestCaller.clearAllWatchers();
                // Create a newInstanceWatcher
                newInstanceWatcher = new ConcreteWatcher();
                // Add watchers to this loginCli
                loginRequestCaller.addWatcher(newInstanceWatcher);
                // Notify observer that a new instance of LoginCli has been instantiated
                loginRequestCaller.notifyWatchers("A new Instance of Login Request Caller has been created");
            }
        }
        return loginRequestCaller;
    }

    public HttpResponse userLogin(JSONObject loginData, Integer roleCode, Boolean jwtRequest) throws IOException, InterruptedException {
        // Performing a valid GET request to fetch a particular resource by ID
        String jsonString = loginData.toString();
        
        JSONObject userData = UserManager.getInstance().getUserByUserName(loginData.getString(USERNAME_KEY));

        String searchKey = UserState.getRoles().get(roleCode);
        if (!userData.getBoolean(searchKey)) {
            throw new IllegalArgumentException("role does not match role given");
        }

        String userIdUrl = ServerConfig.USER_URL + "/login?" + "jwt=" + jwtRequest.toString();
        HttpRequest request = HttpRequest
            .newBuilder(URI.create(userIdUrl))
            .setHeader(ServerConfig.AUTH_HEADER_KEY, ServerConfig.getInstance().getApiKey())
            .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
            .POST(HttpRequest.BodyPublishers.ofString(jsonString))
            .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse verifyToken(String jwt) throws IOException, InterruptedException {
        /*
        Part 5a - Verifying a JWT using the POST /user/verify-token endpoint
        */

        String jsonString = "{\"jwt\":\"" + jwt + "\"}";

        // Note the POST() method being used here, and the request body is supplied to it.
        // A request body needs to be supplied to this endpoint, otherwise a 400 Bad Request error will be returned.
        String usersVerifyTokenUrl = ServerConfig.USER_URL + "/verify-token";
        HttpRequest request = HttpRequest.newBuilder(URI.create(usersVerifyTokenUrl)) // Return a JWT
        .setHeader(ServerConfig.AUTH_HEADER_KEY, ServerConfig.getInstance().getApiKey())
        .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
        .POST(HttpRequest.BodyPublishers.ofString(jsonString))
        .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());

    }

}
