package dev.plan9better.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ReviewbaseDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReviewbaseDataApplication.class, args);
    }
}