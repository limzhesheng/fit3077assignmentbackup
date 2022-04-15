package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fit3077.assignment2.config.ServerConfig;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRequestCaller {
    private static final String USER_URL = ServerConfig.ROOT_URL + "/user";

    private static final HttpClient client = HttpClient.newHttpClient();

    private static final String AUTH_HEADER_KEY = "Authorization";

    private String apiKey;

    public UserRequestCaller() {
        this.apiKey = ServerConfig.apiKey();
    }

    @GetMapping("/{id}")
    public HttpResponse getUserById(@PathVariable String id) throws IOException, InterruptedException {
        // this is where you do stuff basically
        // Performing a valid GET request to fetch a particular resource by ID

        String userIdUrl = USER_URL + "/" + id;
        HttpRequest request = HttpRequest
            .newBuilder(URI.create(userIdUrl))
            .setHeader(AUTH_HEADER_KEY, apiKey)
            .GET()
            .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

}
