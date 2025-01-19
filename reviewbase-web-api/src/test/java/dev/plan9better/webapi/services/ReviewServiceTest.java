package dev.plan9better.webapi.services;

import dev.plan9better.data.model.Review;
import dev.plan9better.data.model.enums.Constructiveness;
import dev.plan9better.data.repository.ReviewRepository;
import dev.plan9better.steamclient.contract.ClassifierResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void classifyReviews_setsConstructivenessToConstructive_whenClassifierReturnsOne() {
        Review review = new Review();
        review.setReview("Great game!");
        ClassifierResponse classifierResponse = new ClassifierResponse(1);
        when(restTemplate.exchange(anyString(), any(), any(), eq(ClassifierResponse.class)))
                .thenReturn(new ResponseEntity<>(classifierResponse, HttpStatus.OK));

        List<Review> result = reviewService.classifyReviews(List.of(review));

        assertEquals(Constructiveness.CONSTRUCTIVE, result.get(0).getConstructiveness());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void classifyReviews_setsConstructivenessToNonConstructive_whenClassifierReturnsZero() {
        Review review = new Review();
        review.setReview("Bad game!");
        ClassifierResponse classifierResponse = new ClassifierResponse(0);
        when(restTemplate.exchange(anyString(), any(), any(), eq(ClassifierResponse.class)))
                .thenReturn(new ResponseEntity<>(classifierResponse, HttpStatus.OK));

        List<Review> result = reviewService.classifyReviews(List.of(review));

        assertEquals(Constructiveness.NON_CONSTRUCTIVE, result.get(0).getConstructiveness());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void classifyReviews_throwsException_whenClassifierResponseIsNull() {
        Review review = new Review();
        review.setReview("Uncertain game!");
        when(restTemplate.exchange(anyString(), any(), any(), eq(ClassifierResponse.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reviewService.classifyReviews(List.of(review));
        });

        assertEquals("ML Classifier endpoint not accessible", exception.getMessage());
    }

    @Test
    void classifyReviews_throwsException_whenClassifierReturnsInvalidValue() {
        Review review = new Review();
        review.setReview("Confusing game!");
        ClassifierResponse classifierResponse = new ClassifierResponse(2);
        when(restTemplate.exchange(anyString(), any(), any(), eq(ClassifierResponse.class)))
                .thenReturn(new ResponseEntity<>(classifierResponse, HttpStatus.OK));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reviewService.classifyReviews(List.of(review));
        });

        assertEquals("ML Classifier wrong verdict: 2 instead of 1 or 0", exception.getMessage());
    }

    @Test
    void classifyReviews_followsRedirect_whenClassifierReturnsRedirect() {
        Review review = new Review();
        review.setReview("Redirect game!");
        ClassifierResponse classifierResponse = new ClassifierResponse(1);
        ResponseEntity<ClassifierResponse> redirectResponse = new ResponseEntity<>(null, HttpStatus.TEMPORARY_REDIRECT);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:13337/redirect"));
        redirectResponse = new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
        when(restTemplate.exchange(anyString(), any(), any(), eq(ClassifierResponse.class)))
                .thenReturn(redirectResponse)
                .thenReturn(new ResponseEntity<>(classifierResponse, HttpStatus.OK));

        List<Review> result = reviewService.classifyReviews(List.of(review));

        assertEquals(Constructiveness.CONSTRUCTIVE, result.get(0).getConstructiveness());
        verify(reviewRepository, times(1)).save(review);
    }
}