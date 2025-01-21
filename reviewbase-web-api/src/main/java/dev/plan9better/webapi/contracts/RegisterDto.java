package dev.plan9better.webapi.contracts;

public record RegisterDto (
    String username,
    String password,
    String email
){}
