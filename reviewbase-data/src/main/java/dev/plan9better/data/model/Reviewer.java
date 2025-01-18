package dev.plan9better.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/*
      "author": {
        "steamid": "76561198964483408",
        "num_games_owned": 0,
        "num_reviews": 10,
        "playtime_forever": 2191,
        "playtime_last_two_weeks": 0,
        "playtime_at_review": 2191,
        "last_played": 1730933095
      },
 */

@Entity
@Data
public class Reviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceId;
    private int numGamesOwned;
    private int numReviews;
    private int playtimeForever;
    private int playtimeLastTwoWeeks;
    private int playtimeAtReview;
    private long lastPlayed;

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Review> reviews;
}