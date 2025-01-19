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

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSourceId() {
        return sourceId;
    }
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
    public int getNumGamesOwned() {
        return numGamesOwned;
    }
    public void setNumGamesOwned(int numGamesOwned) {
        this.numGamesOwned = numGamesOwned;
    }
    public int getNumReviews() {
        return numReviews;
    }
    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }
    public int getPlaytimeForever() {
        return playtimeForever;
    }
    public void setPlaytimeForever(int playtimeForever) {
        this.playtimeForever = playtimeForever;
    }
    public int getPlaytimeLastTwoWeeks() {
        return playtimeLastTwoWeeks;
    }
    public void setPlaytimeLastTwoWeeks(int playtimeLastTwoWeeks) {
        this.playtimeLastTwoWeeks = playtimeLastTwoWeeks;
    }
    public int getPlaytimeAtReview() {
        return playtimeAtReview;
    }
    public void setPlaytimeAtReview(int playtimeAtReview) {
        this.playtimeAtReview = playtimeAtReview;
    }
    public long getLastPlayed() {
        return lastPlayed;
    }
    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}