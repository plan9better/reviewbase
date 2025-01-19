package dev.plan9better.updater.controller;

import dev.plan9better.steamclient.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdaterControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private UpdaterController updaterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void startUpdate_returnsUpdatedMessage_whenGamesAreFetchedAndSaved() {
        List<String> games = List.of("Game1", "Game2");
        when(gameService.fetchGameList()).thenReturn(games);

        ResponseEntity<Object> response = updaterController.startUpdate();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated 2 games", response.getBody());
        verify(gameService, times(1)).SaveSimpleGames(games);
    }

    @Test
    void startUpdate_returnsUpdatedMessage_whenNoGamesAreFetched() {
        List<String> games = List.of();
        when(gameService.fetchGameList()).thenReturn(games);

        ResponseEntity<Object> response = updaterController.startUpdate();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated 0 games", response.getBody());
        verify(gameService, times(1)).SaveSimpleGames(games);
    }

    @Test
    void startUpdate_handlesException_whenGameServiceFails() {
        when(gameService.fetchGameList()).thenThrow(new RuntimeException("Service failure"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            updaterController.startUpdate();
        });

        assertEquals("Service failure", exception.getMessage());
        verify(gameService, never()).SaveSimpleGames(any());
    }
}