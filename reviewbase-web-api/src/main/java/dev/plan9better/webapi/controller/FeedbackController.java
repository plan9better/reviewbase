package dev.plan9better.webapi.controller;

import dev.plan9better.data.model.AppUser;
import dev.plan9better.data.model.Feedback;
import dev.plan9better.data.model.Review;
import dev.plan9better.data.model.enums.Verdict;
import dev.plan9better.data.repository.AppUserRepository;
import dev.plan9better.data.repository.FeedbackRepository;
import dev.plan9better.data.repository.ReviewRepository;
import dev.plan9better.webapi.contracts.SubmitFeedbackDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin("*")
public class FeedbackController {
    private final FeedbackRepository feedbackRepository;
    private final AppUserRepository appUserRepository;
    private final ReviewRepository reviewRepository;
    public FeedbackController(FeedbackRepository feedbackRepository, AppUserRepository appUserRepository, ReviewRepository reviewRepository){
        this.feedbackRepository = feedbackRepository;
        this.appUserRepository = appUserRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping
    public ResponseEntity<String> report(@RequestBody SubmitFeedbackDto fb){
        Feedback feedback = new Feedback();
        feedback.setComment(fb.comment());

        AppUser author = appUserRepository.findByUsername(fb.username());
        if(author == null){
            return ResponseEntity.badRequest().build();
        }
        feedback.setAuthor(author);
        Optional<Review> subject = reviewRepository.findById(fb.reviewId());
        if(subject.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        feedback.setSubject(subject.get());
        feedback.setVerdict(Verdict.PENDING);
        feedbackRepository.save(feedback);
        return ResponseEntity.ok("Saved feedback");
    }

}
