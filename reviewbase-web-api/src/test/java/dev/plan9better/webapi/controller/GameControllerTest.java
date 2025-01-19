package dev.plan9better.webapi.controller;

import dev.plan9better.data.model.Game;
import dev.plan9better.data.model.Review;
import dev.plan9better.data.model.Reviewer;
import dev.plan9better.data.model.enums.Constructiveness;
import dev.plan9better.data.repository.GameRepository;
import dev.plan9better.data.repository.ReviewRepository;
import dev.plan9better.steamclient.service.GameService;
import dev.plan9better.webapi.contracts.ReviewDto;
import dev.plan9better.webapi.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private GameService gameService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGameReviews_GameFound_ReviewsExist() {
        // Arrange
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);

        Reviewer author = new Reviewer();
        author.setId(1L);

        Review review = new Review();
        review.setId(1L);
        review.setConstructiveness(Constructiveness.CONSTRUCTIVE);
        review.setReview("Great game!");
        review.setAuthor(author); // Ensure author is not null

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(reviewRepository.findAllByGame(game)).thenReturn(List.of(review));

        // Act
        ResponseEntity<List<ReviewDto>> response = gameController.getGameReviews(gameId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Great game!", response.getBody().get(0).getContent());
        assertEquals(1L, response.getBody().get(0).getAuthor_id()); // Verify author_id
    }

    @Test
    void testGetGameReviews_GameNotFound() {
        // Arrange
        Long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<ReviewDto>> response = gameController.getGameReviews(gameId);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetGameReviews_NoReviews_FetchAndClassify() {
        // Arrange
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);

        Review review = new Review();
        review.setId(1L);
        review.setConstructiveness(Constructiveness.CONSTRUCTIVE);
        review.setReview("Great game!");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(reviewRepository.findAllByGame(game)).thenReturn(Collections.emptyList());
        when(gameService.fetchGameReviews(game)).thenReturn(List.of(review));
        when(reviewService.classifyReviews(List.of(review))).thenReturn(List.of(review));

        // Act
        ResponseEntity<List<ReviewDto>> response = gameController.getGameReviews(gameId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Great game!", response.getBody().get(0).getContent());
    }

    @Test
    void testGetGameReviews_FilterNonConstructiveReviews() {
        // Arrange
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);

        Review constructiveReview = new Review();
        constructiveReview.setId(1L);
        constructiveReview.setConstructiveness(Constructiveness.CONSTRUCTIVE);
        constructiveReview.setReview("Great game!");

        Review nonConstructiveReview = new Review();
        nonConstructiveReview.setId(2L);
        nonConstructiveReview.setConstructiveness(Constructiveness.NON_CONSTRUCTIVE);
        nonConstructiveReview.setReview("Bad game!");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(reviewRepository.findAllByGame(game)).thenReturn(List.of(constructiveReview, nonConstructiveReview));

        // Act
        ResponseEntity<List<ReviewDto>> response = gameController.getGameReviews(gameId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Great game!", response.getBody().get(0).getContent());
    }

    @Test
    void testSearchGames() {
        // Arrange
        String query = "game";
        Game game1 = new Game();
        game1.setId(1L);
        game1.setTitle("Game 1");

        Game game2 = new Game();
        game2.setId(2L);
        game2.setTitle("Game 2");

        when(gameRepository.findByTitleContaining(query)).thenReturn(List.of(game1, game2));

        // Act
        ResponseEntity<List<Game>> response = gameController.hello(query);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }
}