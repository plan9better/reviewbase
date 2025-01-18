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
    private long timestampCreated;
    private long timestampUpdated;
    private boolean votedUp;
    private int votesUp;
    private int votesFunny;
    private double weightedVoteScore;
    private int commentCount;
    private boolean steamPurchase;
    private boolean receivedForFree;
    private boolean writtenDuringEarlyAccess;
    private boolean primarilySteamDeck;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Reviewer author;

}
