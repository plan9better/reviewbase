package dev.plan9better.steamclient.contract;

import lombok.Data;

import java.util.Map;

@Data
public class GameDetailResponse {
    private Map<String, GameData> data;

    public Map<String, GameData> getData() {
        return data;
    }
}