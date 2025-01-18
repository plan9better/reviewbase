package dev.plan9better.steamclient.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    private long appid;
    private String name;

    public String getName() {
        return name;
    }

    public long getAppid() {
        return appid;
    }
}
