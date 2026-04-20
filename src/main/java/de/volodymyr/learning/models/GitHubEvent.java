package de.volodymyr.learning.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubEvent(
        String type,
        Repo repo,
        @JsonProperty("created_at") String createdAt
) {}