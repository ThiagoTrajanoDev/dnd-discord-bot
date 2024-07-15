package com.bot.RequestManager;



import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestManager {


    public static HttpResponse<String> makeGETRequest(String url) throws Exception {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        return httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
    }
}
