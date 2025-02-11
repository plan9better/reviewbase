package dev.plan9better.steamclient.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AppList(
        @JsonProperty("apps") List<SimpleGameDto> apps
) {}