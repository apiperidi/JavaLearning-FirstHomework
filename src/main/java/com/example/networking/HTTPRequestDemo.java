package com.example.networking;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HTTPRequestDemo {
    public static void main(String[] args) {
        HTTPRequestDemo httpRequestDemo = new HTTPRequestDemo();
        httpRequestDemo.httpSynchronous();
        httpRequestDemo.httpAsynchronous();
    }

    public void httpSynchronous(){
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://www.codehub.gr")).build();
        HttpResponse<String> response = null;

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (HttpURLConnection.HTTP_OK == response.statusCode()){
            System.out.println("Everything is ok!");
            System.out.println("Page content: "+response.body());
        }
        else if(HttpURLConnection.HTTP_NOT_FOUND == response.statusCode()){
            System.out.println("Page not found");
        }
    }

    public void httpAsynchronous(){
        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://www.codehub.gr")).GET().build();
        HttpResponse<String> response = null;

        CompletableFuture<HttpResponse<String>> futureResponse = httpClient.sendAsync(request,
                HttpResponse.BodyHandlers.ofString());

        try {
            response = futureResponse.get();
            System.out.println("Status code: "+response.statusCode());
            System.out.println("Uri: "+response.uri());
            System.out.println("Headers: "+response.headers());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
