package dev.plan9better.data.model;

import dev.plan9better.data.model.enums.Constructiveness;
import jakarta.persistence.*;
import lombok.Data;

/*
  "reviews": [
    {
      "recommendationid": "185479378",
      "author": {
        "steamid": "76561198964483408",
        "num_games_owned": 0,
        "num_reviews": 10,
        "playtime_forever": 2191,
        "playtime_last_two_weeks": 0,
        "playtime_at_review": 2191,
        "last_played": 1730933095
      },
      "language": "english",
      "review": "One of the few games that outlived it's community. \n\nThe moment you enter the game you are transported back to a time, unhurt and in pristine condition. a time-capsule of enjoyment by every metric. The memes, lingo, hats, characters, cinematics, voice-lines, guns, cosmetics, maps, updates, art-style and the music, every single thing TF2 has produced is known by all, seen by all, played by all. Whereas games of the same nature keep degrading, TF2 is still alive and well. \n\nThank you Valve.",
      "timestamp_created": 1736727106,
      "timestamp_updated": 1736727106,
      "voted_up": true,
      "votes_up": 6,
      "votes_funny": 1,
      "weighted_vote_score": "0.603322744369506836",
      "comment_count": 0,
      "steam_purchase": true,
      "received_for_free": false,
      "written_during_early_access": false,
      "primarily_steam_deck": false
    },
 */

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Constructiveness constructiveness;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    // Steam given attributes
    private String sourceId;
    private String language;
    @Column(length = 8192)
    private String review;
    private boolean votedUp;
//    private long timestampCreated;
//    private long timestampUpdated;
//    private boolean votedUp;
//    private int votesUp;
//    private int votesFunny;
//    private double weightedVoteScore;
//    private int commentCount;
//    private boolean steamPurchase;
//    private boolean receivedForFree;
//    private boolean writtenDuringEarlyAccess;
//    private boolean primarilySteamDeck;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Reviewer author;

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Constructiveness getConstructiveness() {
        return constructiveness;
    }
    public void setConstructiveness(Constructiveness constructiveness) {
        this.constructiveness = constructiveness;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public String getSourceId() {
        return sourceId;
    }
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
//    public long getTimestampCreated() {
//        return timestampCreated;
//    }
//    public void setTimestampCreated(long timestampCreated) {
//        this.timestampCreated = timestampCreated;
//    }
//    public long getTimestampUpdated() {
//        return timestampUpdated;
//    }
//    public void setTimestampUpdated(long timestampUpdated) {
//        this.timestampUpdated = timestampUpdated;
//    }
//    public boolean isVotedUp() {
//        return votedUp;
//    }
//    public void setVotedUp(boolean votedUp) {
//        this.votedUp = votedUp;
//    }
//    public int getVotesUp() {
//        return votesUp;
//    }
//    public void setVotesUp(int votesUp) {
//        this.votesUp = votesUp;
//    }
//    public int getVotesFunny() {
//        return votesFunny;
//    }
//    public void setVotesFunny(int votesFunny) {
//        this.votesFunny = votesFunny;
//    }
//    public double getWeightedVoteScore() {
//        return weightedVoteScore;
//    }
//    public void setWeightedVoteScore(double weightedVoteScore) {
//        this.weightedVoteScore = weightedVoteScore;
//    }
//    public int getCommentCount() {
//        return commentCount;
//    }
//    public void setCommentCount(int commentCount) {
//        this.commentCount = commentCount;
//    }
//    public boolean isSteamPurchase() {
//        return steamPurchase;
//    }
//    public void setSteamPurchase(boolean steamPurchase) {
//        this.steamPurchase = steamPurchase;
//    }
//    public boolean isReceivedForFree() {
//        return receivedForFree;
//    }
//    public void setReceivedForFree(boolean receivedForFree) {
//        this.receivedForFree = receivedForFree;
//    }
//    public boolean isWrittenDuringEarlyAccess() {
//        return writtenDuringEarlyAccess;
//    }
//    public void setWrittenDuringEarlyAccess(boolean writtenDuringEarlyAccess) {
//        this.writtenDuringEarlyAccess = writtenDuringEarlyAccess;
//    }
//    public boolean isPrimarilySteamDeck() {
//        return primarilySteamDeck;
//    }
//    public void setPrimarilySteamDeck(boolean primarilySteamDeck) {
//        this.primarilySteamDeck = primarilySteamDeck;
//    }
    public Reviewer getAuthor() {
        return author;
    }
    public void setAuthor(Reviewer author) {
        this.author = author;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
    public boolean isVotedUp() {
        return votedUp;
    }
    public void setVotedUp(boolean votedUp) {
        this.votedUp = votedUp;
    }


}
