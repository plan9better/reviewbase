package dev.plan9better.steamclient.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AppListResponse(
        @JsonProperty("applist") AppList applist
) {}