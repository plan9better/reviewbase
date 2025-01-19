package dev.plan9better.steamclient.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SimpleGameDto(
        @JsonProperty("appid") Long appId,
        @JsonProperty("name") String name
) {}