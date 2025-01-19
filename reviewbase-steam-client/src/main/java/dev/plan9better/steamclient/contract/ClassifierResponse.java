package dev.plan9better.steamclient.contract;


import com.fasterxml.jackson.annotation.JsonProperty;

public record ClassifierResponse(@JsonProperty("constructive") int constructiveness){
}
