package dev.plan9better.steamclient.contract;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GameDetails {
    private String type;
    private String name;
    private String developer;
    private String publisher;
    private List<String> genres;
    private List<String> platforms;
    private Map<String, Double> price;

    public String getDeveloper() {
        return developer;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getType() {
        return type;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public Map<String, Double> getPrice() {
        return price;
    }
}