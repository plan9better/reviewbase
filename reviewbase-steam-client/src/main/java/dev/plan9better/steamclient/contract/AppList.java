package dev.plan9better.steamclient.contract;

import lombok.Data;

import java.util.List;

@Data
public class AppList {
    private List<GameDto> apps;

    public List<GameDto> getApps() {
        return apps;
    }
}
