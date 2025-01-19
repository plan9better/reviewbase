package dev.plan9better.webapi.controller;

import dev.plan9better.data.model.Game;
import dev.plan9better.data.model.Review;
import dev.plan9better.data.model.enums.Constructiveness;
import dev.plan9better.data.repository.GameRepository;
import dev.plan9better.data.repository.ReviewRepository;
import dev.plan9better.steamclient.service.GameService;
import dev.plan9better.webapi.contracts.ReviewDto;
import dev.plan9better.webapi.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1/game")
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    private final GameRepository gameRepository;
    private final ReviewRepository reviewRepository;
    private final GameService gameService;
    private final ReviewService reviewService;

    public GameController(GameRepository gameRepository, ReviewRepository reviewRepository, GameService gameService, ReviewService reviewService) {
        this.gameRepository = gameRepository;
        this.reviewRepository = reviewRepository;
        this.gameService = gameService;
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ReviewDto>> getGameReviews(@PathVariable Long id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Ensure reviews is a mutable list
        List<Review> reviews = new ArrayList<>(reviewRepository.findAllByGame(game.get()));

        if (reviews.isEmpty()) {
            reviews = new ArrayList<>(gameService.fetchGameReviews(game.get()));
            reviews = new ArrayList<>(reviewService.classifyReviews(reviews));
        }

        // Now removeIf will work
        reviews.removeIf(review -> review.getConstructiveness() != Constructiveness.CONSTRUCTIVE);

        List<ReviewDto> reviewDtos = reviews.stream()
                .filter(review -> review.getAuthor() != null) // Filter out reviews with null author
                .map(review -> {
                    ReviewDto reviewDto = new ReviewDto();
                    reviewDto.setConstructiveness(review.getConstructiveness());
                    reviewDto.setContent(review.getReview());
                    reviewDto.setAuthor_id(review.getAuthor().getId()); // Safe to call getId() now
                    reviewDto.setId(review.getId());
                    return reviewDto;
                })
                .toList();

        return ResponseEntity.ok(reviewDtos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Game>> hello(@RequestParam String query) {
        var games = gameRepository.findByTitleContaining(query);
        return ResponseEntity.ok(games.subList(0, Math.min(10, games.size())));
    }
}