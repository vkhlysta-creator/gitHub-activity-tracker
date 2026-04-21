package de.volodymyr.learning;


import de.volodymyr.learning.ActivityService.GitHubService;

import de.volodymyr.learning.cache.CacheManager;

import de.volodymyr.learning.models.GitHubEvent;


import java.util.List;
import java.util.Optional;



public class Main {
    public static void main(String[] args) {

            if (args.length < 1){
                System.out.println("Guide: github-activity <username>");
                return;
            }

            Optional<List<GitHubEvent>> listEvents = CacheManager.getCache(args[0]);
            listEvents.ifPresent(GitHubService::displayActivity);

            if (listEvents.isEmpty()){
                System.out.println("Could not retrieve data for user" + args[0] + ". Check connection or username.");
            }

    }
}