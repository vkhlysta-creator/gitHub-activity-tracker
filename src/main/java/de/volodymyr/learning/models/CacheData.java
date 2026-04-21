package de.volodymyr.learning.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CacheData(LocalDateTime createdAt, List<GitHubEvent> events){}
