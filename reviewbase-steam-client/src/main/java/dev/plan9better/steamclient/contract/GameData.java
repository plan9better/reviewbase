package dev.plan9better.steamclient.contract;

import lombok.Data;

@Data
public class GameData {
    private boolean success;
    private GameDetails details;

    public GameDetails getDetails() {
        return details;
    }
    public boolean isSuccess() {
        return success;
    }
}