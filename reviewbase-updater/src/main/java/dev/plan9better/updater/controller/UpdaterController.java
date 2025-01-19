package dev.plan9better.updater.controller;

import dev.plan9better.steamclient.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/updater")
public class UpdaterController {
    private final GameService gameService;
    public UpdaterController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/start/all")
    public ResponseEntity<Object> startUpdate() {
        var games = gameService.fetchGameList();
        int n = games.size();
        gameService.SaveSimpleGames(games);
        return ResponseEntity.ok("Updated " + n + " games");
    }
}
