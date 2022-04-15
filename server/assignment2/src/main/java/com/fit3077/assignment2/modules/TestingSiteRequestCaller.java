package com.fit3077.assignment2.modules;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fit3077.assignment2.config.ServerConfig;
import com.fit3077.assignment2.config.return_types.SimpleResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-site")
public class TestingSiteRequestCaller {
    private static final String TESTING_SITE_URL = ServerConfig.ROOT_URL + "/testing-site";

    private static final HttpClient client = HttpClient.newHttpClient();

    private static final String AUTH_HEADER_KEY = "Authorization";

    private String apiKey;

    public TestingSiteRequestCaller() {
        this.apiKey = ServerConfig.apiKey();
        // // A sample of the API request to search for testing sites. 
        // try {
        //     System.out.println(this.searchTestingSiteSample("Clayton", "", ""));
        //     System.out.println("");
        // } catch (IOException | InterruptedException e) {
        //     System.err.println(e);
        // }

        // this.apiKey = "bogusKey";
        // // Sample of unauthorized GET request. 
        // try {
        //     System.out.println(this.searchTestingSiteSample("Clayton", "", ""));
        //     System.out.println("");
        // } catch (IOException | InterruptedException e) {
        //     System.err.println(e);
        // }
    }

    /**
     * API request to search for testing sites. 
     * @param suburbName
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("")
    public SimpleResponse searchTestingSite(@PathVariable String suburbName, 
    @PathVariable(required = false) String functions, @PathVariable(required = false) String types) throws IOException, InterruptedException {
        // this is where you do stuff basically
        // Performing a valid GET request to fetch a particular resource by ID


        String address = "address";
        String suburb = "suburb";   // the "suburb" property is available under the "address" property
        String additionalInfo = "additionalInfo";

        // // the properties below can be categorised in "additionalInfo"
        // String driveThrough = "driveThrough";
        // String walkIn = "walkIn";
        // String[] siteFunctions = {driveThrough, walkIn};

        // String clinic = "clinic";
        // String generalPractitioner = "gp";
        // String hospital = "hospital";
        // String[] siteTypes = {clinic, generalPractitioner, hospital};
        String[] siteFunctions = {};
        if (functions != null && functions.strip().length() != 0) siteFunctions = functions.split(ServerConfig.DELIMITER);
        String[] siteTypes = {};
        if (types != null && types.strip().length() != 0) siteTypes = types.split(ServerConfig.DELIMITER);

        String userIdUrl = TESTING_SITE_URL + "/?suburb=" + suburbName;
        HttpRequest request = HttpRequest
            .newBuilder(URI.create(userIdUrl))
            .setHeader(AUTH_HEADER_KEY, apiKey)
            .GET()
            .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONArray validTestingSites;
        if (response.statusCode() != 200) {
            return new SimpleResponse<>(response.statusCode(), response.uri(), new JSONObject(response.body()));
        }
        JSONArray testingSites = new JSONArray(response.body().toString());
        List<JSONObject> matchingSites = new ArrayList<>();

        for (int x = 0; x < testingSites.length(); x++) {
            JSONObject testSite = new JSONObject(testingSites.get(x).toString());
            Boolean nameMatch = testSite.getJSONObject(address).getString(suburb).equals(suburbName);
    
            // If facility type/function is specified, 
            Boolean typeMatch = siteTypes.length == 0;
            for (int f = 0; f < siteTypes.length; f++) {
                typeMatch = testSite.getJSONObject(additionalInfo).getBoolean(siteTypes[f]);
                if (Boolean.TRUE.equals(typeMatch)) break;
            }
    
            Boolean functionMatch = siteFunctions.length == 0;
            for (int f = 0; f < siteFunctions.length; f++) {
                functionMatch = testSite.getJSONObject(additionalInfo).getBoolean(siteFunctions[f]);
                if (Boolean.TRUE.equals(functionMatch)) break;
            }
    
            if (Boolean.TRUE.equals(nameMatch && typeMatch && functionMatch)) {
                matchingSites.add(testSite);
            }
        }
    
        validTestingSites = new JSONArray(matchingSites);
    
        for (int x = 0; x < validTestingSites.length(); x++) {
            System.out.println(validTestingSites.get(x).toString());
        }
        
        return new SimpleResponse<JSONArray>(response.statusCode(), response.uri(), validTestingSites);
    }
}
