package dev.plan9better.steamclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "dev.plan9better")
public class ReviewbaseSteamclientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReviewbaseSteamclientApplication.class, args);
    }
}