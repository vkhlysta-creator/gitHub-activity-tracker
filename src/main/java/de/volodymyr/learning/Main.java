package de.volodymyr.learning;


import de.volodymyr.learning.GitHubClient.GitHubClient;
import de.volodymyr.learning.exceptions.UserNotFound;

import java.net.URISyntaxException;

import static de.volodymyr.learning.ActivityService.GitHubService.displayActivity;
import static de.volodymyr.learning.ActivityService.GitHubService.getEventsAsList;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length < 1){
                System.out.println("Guide: github-activity <username>");
                return;
            }
            displayActivity(getEventsAsList(GitHubClient.fetchRawEvents(args[0])));
        } catch (URISyntaxException e) {
            System.out.println("URL Syntax");
        } catch (UserNotFound e) {
            System.out.println("User not Found. Check the spelling");
        }
    }
}