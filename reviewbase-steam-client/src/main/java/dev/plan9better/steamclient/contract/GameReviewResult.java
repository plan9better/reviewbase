package dev.plan9better.steamclient.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GameReviewResult(
        int success,
        @JsonProperty("query_summary")
        QuerySummary querySummary,
        @JsonProperty("reviews")
        List<GameReview> reviews
) {
    public record QuerySummary(
            @JsonProperty("total_reviews")
            int totalReviews,
            @JsonProperty("review_score")
            int reviewScore
    ) {}

    public record GameReview(
            @JsonProperty("recommendationid")
            String recommendationId,
            @JsonProperty("review")
            String review,
            @JsonProperty("author")
            Author author,
            @JsonProperty("timestamp_created")
            long timestampCreated,
            @JsonProperty("voted_up")
            boolean votedUp,
            @JsonProperty("votes_up")
            int votesUp,
            @JsonProperty("votes_funny")
            int votesFunny,
            @JsonProperty("weighted_vote_score")
            double weightedVoteScore,
            @JsonProperty("comment_count")
            int commentCount,
            @JsonProperty("steam_purchase")
            boolean steamPurchase,
            @JsonProperty("received_for_free")
            boolean receivedForFree,
            @JsonProperty("written_during_early_access")
            boolean writtenDuringEarlyAccess
    ) {
       public record Author(
                @JsonProperty("steamid")
                String steamId,
                @JsonProperty("num_games_owned")
                int numGamesOwned,
                @JsonProperty("num_reviews")
                int numReviews,
                @JsonProperty("playtime_forever")
                int playtimeForever,
                @JsonProperty("playtime_last_two_weeks")
                int playtimeLastTwoWeeks,
                @JsonProperty("last_played")
                long lastPlayed
       ){}
    }
}
