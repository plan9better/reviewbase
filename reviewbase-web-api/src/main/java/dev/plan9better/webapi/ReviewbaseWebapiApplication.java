package dev.plan9better.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "dev.plan9better")
public class ReviewbaseWebapiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReviewbaseWebapiApplication.class, args);
    }
}