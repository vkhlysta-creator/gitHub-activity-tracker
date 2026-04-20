package de.volodymyr.learning.ActivityService;

import de.volodymyr.learning.GitHubClient.GitHubClient;
import de.volodymyr.learning.models.GitHubEvent;
import de.volodymyr.learning.exceptions.UserNotFound;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


public class GitHubService {

    public static void main(String[] args) {
        try {
            displayActivity(getEventsAsList(GitHubClient.fetchRawEvents("almasi")));
        } catch (URISyntaxException e) {
            System.out.println("URL Syntax");
        } catch (UserNotFound e) {
            System.out.println("User not Found. Check the spelling");
        }
    }

    public static List<GitHubEvent> getEventsAsList(String json) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<>() {
        });

    }

    public static void displayActivity(List<GitHubEvent> events) {
        if (events.isEmpty()) {
            System.out.println("User was inactive for a long time");
            return;
        }
        String resultString = events.stream()
                .limit(10)
                .map(
                        event -> {
                            switch (event.type()) {

                                case "PushEvent" -> {
                                    return "– Pushed to " + event.repo().name() + "\n";
                                }
                                case "WatchEvent" -> {
                                    return "– Starred " + event.repo().name() + "\n";
                                }
                                case "CreateEvent" -> {
                                    return "– Created branch/repo " + event.repo().name() + "\n";
                                }
                                case "IssuesEvent" -> {
                                    return "– Opened an issue in " + event.repo().name() + "\n";
                                }
                                default -> {
                                    return "– " + event.type() + " in " + event.repo().name() + "\n";
                                }

                            }
                        }
                )
                .collect(Collectors.joining());
        System.out.println(resultString);
    }
}
