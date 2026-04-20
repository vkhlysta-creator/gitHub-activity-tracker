package de.volodymyr.learning.GitHubClient;

import de.volodymyr.learning.exceptions.UserNotFound;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubClient {


    public static String fetchRawEvents(String username) throws URISyntaxException, UserNotFound {

        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.github.com/users/" + username + "/events")).build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 404) {
                    throw new UserNotFound("User not found");
                }
                return response.body();
            } catch (IOException e) {
                System.out.println("IOException: ");
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }

        }


        return "";
    }
}
