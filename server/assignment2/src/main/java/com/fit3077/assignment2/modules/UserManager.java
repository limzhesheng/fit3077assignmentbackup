package com.fit3077.assignment2.modules;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fit3077.assignment2.config.ServerConfig;
import com.fit3077.assignment2.jsonEntities.User;
import com.fit3077.assignment2.modules.interfaces.MutableJsonStorage;

import com.fit3077.assignment2.obervers.ConcreteWatched;
import com.fit3077.assignment2.obervers.ConcreteWatcher;
import com.fit3077.assignment2.obervers.Watcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserManager extends ConcreteWatched implements MutableJsonStorage{
    private static UserManager userManager;
    private static Watcher newInstanceWatcher;

    private static final HttpClient client = HttpClient.newHttpClient();

    private Map<String, JSONObject> users;
    private Map<String, User> otherUsers;

    private UserManager() {
        this.refresh();
    }
    
    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
            if (newInstanceWatcher == null) {
                // Clear all previous watchers
                userManager.clearAllWatchers();
                // Create a newInstanceWatcher
                newInstanceWatcher = new ConcreteWatcher();
                // Add watchers to this loginCli
                userManager.addWatcher(newInstanceWatcher);
                // Notify observer that a new instance of LoginCli has been instantiated
                userManager.notifyWatchers("A new Instance of User Manager has been created");
            }
        }
        return userManager;
    }

    public void refresh() {
        users = new HashMap<>();
        try {
            HttpRequest request = HttpRequest
            .newBuilder(URI.create(ServerConfig.USER_URL))
            .setHeader(ServerConfig.AUTH_HEADER_KEY, ServerConfig.getInstance().getApiKey())
            .GET()
            .build();

            HttpResponse resp = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray userArray = new JSONArray(resp.body().toString());

            for (int i = 0; i < userArray.length(); i++) {
                try {
                    JSONObject currObj = userArray.getJSONObject(i);
                    users.put(currObj.getString(ServerConfig.USERNAME_KEY), currObj);
                } catch (JSONException e) {
                    // continue
                }
                
            }
        } catch (Exception e) {
            log.warn("Warning: user data not cached");
        }
    }

    private void refreshOther() {
        otherUsers = new HashMap<>();
        try {
            HttpRequest request = HttpRequest
            .newBuilder(URI.create(ServerConfig.USER_URL))
            .setHeader(ServerConfig.AUTH_HEADER_KEY, ServerConfig.getInstance().getApiKey())
            .GET()
            .build();

            HttpResponse resp = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray userArray = new JSONArray(resp.body().toString());

            for (int i = 0; i < userArray.length(); i++) {
                try {
                    JSONObject currObj = userArray.getJSONObject(i);
                    User newUser = new ModelMapper().map(currObj, User.class);
                    otherUsers.put(newUser.getUserName(), newUser);
                } catch (JSONException e) {
                    // continue
                }
                
            }
        } catch (Exception e) {
            log.warn("Warning: user data not cached");
        }
    }

    public JSONObject getUserByUserName(String userName) {
        return users.get(userName);
    }

    public JSONObject getUserByFullName(String givenName, String familyName) {
        for (Entry<String, JSONObject> k : users.entrySet()) {
            JSONObject currUser = k.getValue();
            try {
                if (currUser.getString(ServerConfig.GIVEN_NAME_KEY).equals(givenName) && 
                currUser.getString(ServerConfig.FAMILY_NAME_KEY).equals(familyName)) {
                    return currUser;
                }

            } catch (Exception e) { /* */ }
        }
        return null;
    }
}
