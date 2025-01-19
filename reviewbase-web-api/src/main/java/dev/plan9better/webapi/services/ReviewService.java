package dev.plan9better.webapi.services;

import dev.plan9better.data.model.Review;
import dev.plan9better.data.model.enums.Constructiveness;
import dev.plan9better.data.repository.ReviewRepository;
import dev.plan9better.steamclient.contract.ClassifierResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    ReviewRepository reviewRepository;
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public List<Review> classifyReviews(List<Review> reviews) {
        RestTemplate restTemplate = new RestTemplate();
        String CLASSIFIER_ENDPOINT_URL = "http://localhost:13337/predict";

        for (var review : reviews) {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("text", review.getReview());

            ResponseEntity<ClassifierResponse> responseEntity = restTemplate.exchange(
                    CLASSIFIER_ENDPOINT_URL,
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody),
                    ClassifierResponse.class
            );

            if (responseEntity.getStatusCode().is3xxRedirection()) {
                // Capture the location URL from the 307 redirect
                String redirectUrl = responseEntity.getHeaders().getLocation().toString();
                // Follow the redirect manually with the new URL
                responseEntity = restTemplate.exchange(
                        redirectUrl,
                        HttpMethod.POST,
                        new HttpEntity<>(requestBody),
                        ClassifierResponse.class
                );
            }

            ClassifierResponse classifierResponse = responseEntity.getBody();
            if (classifierResponse == null) {
                throw new RuntimeException("ML Classifier endpoint not accessible");
            }

            Constructiveness c = Constructiveness.PENDING;
            if (classifierResponse.constructiveness() == 1) {
                c = Constructiveness.CONSTRUCTIVE;
            } else if (classifierResponse.constructiveness() == 0) {
                c = Constructiveness.NON_CONSTRUCTIVE;
            } else {
                throw new RuntimeException("ML Classifier wrong verdict: " + classifierResponse.constructiveness() + " instead of 1 or 0");
            }
            review.setConstructiveness(c);
            reviewRepository.save(review);
        }
        return reviews;
    }
}
