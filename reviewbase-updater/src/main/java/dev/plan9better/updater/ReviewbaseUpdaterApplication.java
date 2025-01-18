package dev.plan9better.updater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "dev.plan9better")
public class ReviewbaseUpdaterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReviewbaseUpdaterApplication.class, args);
    }
}