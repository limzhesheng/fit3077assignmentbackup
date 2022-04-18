package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fit3077.assignment2.config.ServerConfig;

import org.json.JSONObject;

public class LoginRequestCaller {
    // API calls here. Most methods here are really just man-in-the-middle methods that assist the frontend in communicating with the server.

    private static final String USER_URL = ServerConfig.ROOT_URL + "/user";

    private static final HttpClient client = HttpClient.newHttpClient();

    private static final String AUTH_HEADER_KEY = "Authorization";

    private String apiKey;

    public LoginRequestCaller() {
        this.apiKey = ServerConfig.getInstance().getApiKey();
    }


    public HttpResponse userLogin(JSONObject loginData, Boolean jwtRequest) throws IOException, InterruptedException {
        // this is where you do stuff basically
        // Performing a valid GET request to fetch a particular resource by ID
        String jsonString = loginData.toString();

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
