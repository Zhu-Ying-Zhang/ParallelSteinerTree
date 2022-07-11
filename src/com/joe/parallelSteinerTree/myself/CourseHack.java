package com.joe.parallelSteinerTree.myself;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;


public class CourseHack {
    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        ArrayList list = new ArrayList();
        for (int i = 33000; i < 140000; i++) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("" + i))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (!response.body().equals("{\"error_code\":\"404000\",\"message\":\"Not Found\",\"name\":\"NOT_FOUND\"}")) {
                System.out.println("Got you! number is " + i);
                list.add(i);
            } else System.out.println("Not in the lecture " + i);
            Thread.sleep(5000);
        }
        System.out.println(Arrays.toString(list.toArray()));
    }
}
