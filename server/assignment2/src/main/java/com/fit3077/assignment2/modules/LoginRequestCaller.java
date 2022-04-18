package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.fit3077.assignment2.config.ServerConfig;
import com.fit3077.assignment2.config.return_types.UserState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginRequestCaller {
    // API calls here. Most methods here are really just man-in-the-middle methods that assist the frontend in communicating with the server.
    private static LoginRequestCaller loginRequestCaller;
    private static final String USER_URL = ServerConfig.ROOT_URL + "/user";

    private static final HttpClient client = HttpClient.newHttpClient();

    private static final String AUTH_HEADER_KEY = "Authorization";

    private String apiKey;

    private Map<String, JSONObject> users;
    private static final String USERNAME_KEY = "userName";

    private LoginRequestCaller() throws IOException, InterruptedException {
        this.apiKey = ServerConfig.getInstance().getApiKey();
        users = new HashMap<>();
        this.populateUsers();
    }
    
    public static LoginRequestCaller getInstance() throws IOException, InterruptedException {
        if (loginRequestCaller == null) {
            loginRequestCaller = new LoginRequestCaller();
        }
        return loginRequestCaller;
    }

    private void populateUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
            .newBuilder(URI.create(USER_URL))
            .setHeader(AUTH_HEADER_KEY, apiKey)
            .GET()
            .build();

        HttpResponse resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONArray userArray = new JSONArray(resp.body().toString());

        for (int i = 0; i < userArray.length(); i++) {
            try {
                JSONObject currObj = userArray.getJSONObject(i);
                users.put(currObj.getString(USERNAME_KEY), currObj);
            } catch (JSONException e) {
                System.err.println(e);
                System.err.println("");
                // continue
            }
            
        }
    }

    public JSONObject getUserByUserName(String userName) {
        return users.get(userName);
    }

    public HttpResponse userLogin(JSONObject loginData, Integer roleCode, Boolean jwtRequest) throws IOException, InterruptedException {
        // this is where you do stuff basically
        // Performing a valid GET request to fetch a particular resource by ID
        String jsonString = loginData.toString();
        
        JSONObject userData = getUserByUserName(loginData.getString(USERNAME_KEY));

        String searchKey = UserState.getRoles().get(roleCode);
        if (!userData.getBoolean(searchKey)) {
            System.err.println("User role invalid");
            throw new IllegalArgumentException("role does not match role given");
        }

        String userIdUrl = USER_URL + "/login?" + "jwt=" + jwtRequest.toString();
        HttpRequest request = HttpRequest
            .newBuilder(URI.create(userIdUrl))
            .setHeader(AUTH_HEADER_KEY, apiKey)
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
        String usersVerifyTokenUrl = USER_URL + "/verify-token";
        HttpRequest request = HttpRequest.newBuilder(URI.create(usersVerifyTokenUrl)) // Return a JWT so we can use it in Part 5 later.
        .setHeader(AUTH_HEADER_KEY, apiKey)
        .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
        .POST(HttpRequest.BodyPublishers.ofString(jsonString))
        .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // System.out.println(request.uri());
        // System.out.println("Response code: " + response.statusCode());
        // System.out.println("Full JSON response: " + response.body());

        return response;
    }

}
