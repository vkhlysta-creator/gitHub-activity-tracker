package de.volodymyr.learning.cache;

import de.volodymyr.learning.ActivityService.GitHubService;
import de.volodymyr.learning.GitHubClient.GitHubClient;
import de.volodymyr.learning.exceptions.UserNotFound;
import de.volodymyr.learning.models.CacheData;
import de.volodymyr.learning.models.GitHubEvent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CacheManager {
    private final static Path CACHEPATH = Paths.get("src/main/java/de/volodymyr/learning/cache/");

    public static Path getCachePath() {
        return CACHEPATH;
    }



    public static Optional<CacheData> fetchFreshCache(String username) {
        try {
            Optional<String> json = GitHubClient.fetchRawEvents(username);
            if (json.isPresent()) {


                return GitHubService.getEventsAsList(json.get())
                        .map(events -> new CacheData(LocalDateTime.now(), events));
            }
        } catch (UserNotFound e) {
            System.err.println("User not found: " + username);
        } catch (URISyntaxException e) {
            System.err.println("Invalid URL for user: " + username);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }

        return Optional.empty();
    }

    public static void saveToFile(CacheData data, String username) {
        Path usersPath = getCachePath().resolve(username + "_cache.json");

        try {
            Files.createDirectories(usersPath.getParent());

            JsonConfig.getMAPPER().writeValue(usersPath.toFile(), data);

        } catch (IOException e) {
            System.err.println("Failed to save cache for " + username + ": " + e.getMessage());
        }
    }

    public static Optional<CacheData> readFromFile(String username) {
        Path usersPath = getCachePath().resolve(username + "_cache.json");

        if (Files.exists(usersPath)) {
            try {
                CacheData result = JsonConfig.getMAPPER().readValue(usersPath.toFile(), CacheData.class);
                return Optional.of(result);
            } catch (IOException e) {
                System.err.println("IOException while reading File for: " + username + " Message: " + e.getMessage());
            }
        }

        return Optional.empty();

    }

    public static boolean isValideData(LocalDateTime createdAt) {
        return Duration.between(createdAt, LocalDateTime.now()).toMinutes() < 5;
    }

    public static Optional<List<GitHubEvent>> getCache(String username) {
        Optional<CacheData> readCache = readFromFile(username);

        if (readCache.isPresent() && isValideData(readCache.get().createdAt())) {
            System.out.println("[INFO] Data loaded from Cache.");
            List<GitHubEvent> result = readCache.get().events();
            return Optional.of(result);
        }
        System.out.println("[INFO] Data loaded from Network.");
        Optional<CacheData> fetchedData = fetchFreshCache(username);


        fetchedData.ifPresent(cacheData -> saveToFile(cacheData, username));

        return fetchedData.map(CacheData::events);

    }


}
