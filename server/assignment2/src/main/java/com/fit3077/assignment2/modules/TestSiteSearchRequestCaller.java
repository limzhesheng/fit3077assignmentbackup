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
import org.json.JSONException;
import org.json.JSONObject;

public class TestSiteSearchRequestCaller {
    private static final HttpClient client = HttpClient.newHttpClient();

    public TestSiteSearchRequestCaller() throws IOException, InterruptedException {/** */}

    /**
     * API request to search for testing sites. 
     * @param suburbName
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public SimpleResponse<JSONArray> searchTestingSite(String suburbName, String functions, String types) throws IOException, InterruptedException {
        // this is where you do stuff basically
        // Performing a valid GET request to fetch a particular resource by ID

        String address = "address";
        String suburb = "suburb";   // the "suburb" property is available under the "address" property
        String additionalInfo = "additionalInfo";

        String[] siteFunctions = {};
        if (functions != null && functions.strip().length() != 0) siteFunctions = functions.split(ServerConfig.DELIMITER);
        String[] siteTypes = {};
        if (types != null && types.strip().length() != 0) siteTypes = types.split(ServerConfig.DELIMITER);

        HttpRequest request = HttpRequest
            .newBuilder(URI.create(ServerConfig.TESTING_SITE_URL))
            .setHeader(ServerConfig.AUTH_HEADER_KEY, ServerConfig.getInstance().getApiKey())
            .GET()
            .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<JSONObject> matchingSites = new ArrayList<>();

        if (response.statusCode() != 200) {
            return new SimpleResponse<>(response.statusCode(), response.uri(), new JSONArray(matchingSites));
        }
        JSONArray testingSites = new JSONArray(response.body().toString());

        for (int x = 0; x < testingSites.length(); x++) {
            JSONObject testSite = testingSites.getJSONObject(x);
            Boolean nameMatch = suburbName.length() == 0 || 
            (suburbName.length() != 0 && testSite.getJSONObject(address).getString(suburb).equals(suburbName));
    
            // If facility type/function is specified, 
            Boolean typeMatch = siteTypes.length == 0;
            for (int f = 0; f < siteTypes.length; f++) {
                try {
                    typeMatch = testSite.getJSONObject(additionalInfo).getBoolean(siteTypes[f]);
                    if (Boolean.TRUE.equals(typeMatch)) break;
                } catch (JSONException e) { /* skip */ }
            }
    
            Boolean functionMatch = siteFunctions.length == 0;
            for (int f = 0; f < siteFunctions.length; f++) {
                try {
                    functionMatch = testSite.getJSONObject(additionalInfo).getBoolean(siteFunctions[f]);
                    if (Boolean.TRUE.equals(functionMatch)) break;
                } catch (JSONException e) { /* skip */ }
            }
    
            if (Boolean.TRUE.equals(nameMatch && typeMatch && functionMatch)) {
                matchingSites.add(testSite);
            }
        }
        return new SimpleResponse<>(response.statusCode(), response.uri(), new JSONArray(matchingSites));
    }
}
