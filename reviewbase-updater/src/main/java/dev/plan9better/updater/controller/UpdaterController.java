package dev.plan9better.updater.controller;

import dev.plan9better.steamclient.contract.GameDto;
import dev.plan9better.steamclient.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/updater")
@RequiredArgsConstructor
public class UpdaterController {
    private final GameService gameService;

    @GetMapping("/start/{id}")
    public void startUpdate(String id) {
        List<GameDto> games = gameService.fetchGameList();
        List<GameDto> first100 = games.subList(0, 100);
        gameService.fetchAndSaveGameDetails(first100);
    }
}
