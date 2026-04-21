package de.volodymyr.learning.ActivityService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.volodymyr.learning.models.GitHubEvent;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class GitHubService {


    public static Optional<List<GitHubEvent>> getEventsAsList(String json) {
        if (json == null || json.isBlank()) return Optional.empty();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            List<GitHubEvent> events = mapper.readValue(json, new TypeReference<List<GitHubEvent>>() {});
            return Optional.of(events);
        } catch (JsonProcessingException e) {
            System.err.println("Parsing error: " + e.getMessage());
            return Optional.empty();
        }
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
