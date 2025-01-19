package dev.plan9better.steamclient.service;

import dev.plan9better.data.model.Game;
import dev.plan9better.data.model.Review;
import dev.plan9better.data.model.Reviewer;
import dev.plan9better.data.model.enums.Constructiveness;
import dev.plan9better.data.repository.GameRepository;
import dev.plan9better.data.repository.ReviewRepository;
import dev.plan9better.data.repository.ReviewerRepository;
import dev.plan9better.steamclient.contract.AppListResponse;
import dev.plan9better.steamclient.contract.GameReviewResult;
import dev.plan9better.steamclient.contract.SimpleGameDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    public static final String STEAM_API_ALL_GAMES_URL = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";

    private final GameRepository gameRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewerRepository reviewerRepository;

    public GameService(GameRepository gameRepository, ReviewRepository reviewRepository, ReviewerRepository reviewerRepository) {
        this.gameRepository = gameRepository;
        this.reviewRepository = reviewRepository;
        this.reviewerRepository = reviewerRepository;
    }

    public List<SimpleGameDto> fetchGameList() {
        RestTemplate restTemplate = new RestTemplate();
        AppListResponse appListResponse = restTemplate.getForObject(STEAM_API_ALL_GAMES_URL, AppListResponse.class);
        if(appListResponse == null || appListResponse.applist() == null || appListResponse.applist().apps() == null) {
            throw new RuntimeException("Failed to fetch game list");
        }
        return appListResponse.applist().apps();
    }
    public void SaveSimpleGames(List<SimpleGameDto> games) {
        for (SimpleGameDto game : games) {
            if(game.name() != null && game.appId() != null && !game.name().isEmpty()) {
                if(gameRepository.existsByAppId(game.appId())) {
                    continue;
                }
                Game gameEntity = new Game();
                gameEntity.setTitle(game.name());
                gameEntity.setAppId(game.appId());
                gameRepository.save(gameEntity);
            }
        }
    }

    public List<Review> fetchGameReviews(Game game) {
        var reviews = reviewRepository.findAllByGame(game);
        if(!reviews.isEmpty()){
            return reviews;
        }

        RestTemplate restTemplate = new RestTemplate();
        GameReviewResult gameReviewResult = restTemplate.getForObject("https://store.steampowered.com/appreviews/" + game.getAppId() + "?json=1&num_per_page=100", GameReviewResult.class);
        if(gameReviewResult == null || gameReviewResult.reviews() == null || gameReviewResult.success() != 1) {
            throw new RuntimeException("Failed to fetch game reviews");
        }

        List<Review> reviewList = new ArrayList<>();
        for (var review : gameReviewResult.reviews()) {
            Reviewer reviewer = reviewerRepository.findBySourceId(review.author().steamId());
            if(reviewer == null) {
                reviewer = new Reviewer();
                reviewer.setSourceId(review.author().steamId());
                if(reviewer.getReviews() == null){
                    reviewer.setReviews(List.of());
                }
                reviewerRepository.save(reviewer);
            }

            Review reviewEntity = new Review();
            reviewEntity.setGame(game);
            reviewEntity.setAuthor(reviewer);
            reviewEntity.setReview(review.review());
            reviewEntity.setConstructiveness(Constructiveness.PENDING);
            reviewEntity.setSourceId(review.recommendationId());
            reviewEntity.setVotedUp(review.votedUp());
            reviewRepository.save(reviewEntity);
            reviewList.add(reviewEntity);
        }
        return reviewList;
    }
}
