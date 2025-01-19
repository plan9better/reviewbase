package dev.plan9better.webapi.controller;

import dev.plan9better.data.model.AppUser;
import dev.plan9better.data.model.Feedback;
import dev.plan9better.data.model.Review;
import dev.plan9better.data.repository.AppUserRepository;
import dev.plan9better.data.repository.FeedbackRepository;
import dev.plan9better.data.repository.ReviewRepository;
import dev.plan9better.webapi.contracts.SubmitFeedbackDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FeedbackControllerTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private FeedbackController feedbackController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReportFeedback_Success() {
        // Arrange
        SubmitFeedbackDto dto = new SubmitFeedbackDto("testUser", 1L, "Great review!");
        AppUser author = new AppUser();
        author.setUsername("testUser");
        Review review = new Review();
        review.setId(1L);

        when(appUserRepository.findByUsername("testUser")).thenReturn(author);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(new Feedback());

        // Act
        ResponseEntity<String> response = feedbackController.report(dto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Saved feedback", response.getBody());
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void testReportFeedback_UserNotFound() {
        // Arrange
        SubmitFeedbackDto dto = new SubmitFeedbackDto("unknownUser", 1L, "Great review!");

        when(appUserRepository.findByUsername("unknownUser")).thenReturn(null);

        // Act
        ResponseEntity<String> response = feedbackController.report(dto);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        verify(feedbackRepository, never()).save(any(Feedback.class));
    }

    @Test
    void testReportFeedback_ReviewNotFound() {
        // Arrange
        SubmitFeedbackDto dto = new SubmitFeedbackDto("testUser", 999L, "Great review!");
        AppUser author = new AppUser();
        author.setUsername("testUser");

        when(appUserRepository.findByUsername("testUser")).thenReturn(author);
        when(reviewRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = feedbackController.report(dto);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        verify(feedbackRepository, never()).save(any(Feedback.class));
    }
}