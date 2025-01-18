package dev.plan9better.steamclient.contract;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class GameListResponse {
    private AppList applist;

    public AppList getApplist() {
        return applist;
    }
}