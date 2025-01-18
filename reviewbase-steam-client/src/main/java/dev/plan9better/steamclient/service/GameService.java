package dev.plan9better.steamclient.service;

import dev.plan9better.data.model.*;
import dev.plan9better.data.repository.*;
import dev.plan9better.steamclient.contract.GameDetailResponse;
import dev.plan9better.steamclient.contract.GameDetails;
import dev.plan9better.steamclient.contract.GameDto;
import dev.plan9better.steamclient.contract.GameListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final RestTemplate restTemplate;
    private final GameRepository gameRepository;
    private final DeveloperRepository developerRepository;
    private final PublisherRepository publisherRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;

    public List<GameDto> fetchGameList() {
        String gameListEndpoint = "api.steampowered.com/ISteamApps/GetAppList/v0002/?format=json";
        try {
            GameListResponse response = restTemplate.getForObject(gameListEndpoint, GameListResponse.class);
            if (response != null && response.getApplist() != null) {
                return response.getApplist().getApps().stream()
                        .filter(game -> StringUtils.hasText(game.getName())) // Filter out games with blank names
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching game list from endpoint: " + gameListEndpoint, e);
        }
    }

    public void fetchAndSaveGameDetails(List<GameDto> gameDtos) {
        String detailUrlTemplate = "https://store.steampowered.com/api/appdetails?appids=";
        gameDtos.forEach(gameDto -> {
            try {
//                String detailUrl = detailUrlTemplate.replace("{appid}", String.valueOf(gameDto.getAppid()));
                String detailUrl = detailUrlTemplate + gameDto.getAppid();
                GameDetailResponse response = restTemplate.getForObject(detailUrl, GameDetailResponse.class);

                if (response != null && response.getData() != null) {
                    response.getData().values().forEach(gameData -> {
                        if (gameData.isSuccess()) {
                            saveGameDetails(gameDto.getAppid(), gameDto.getName(), gameData.getDetails());
                        }
                    });
                }
            } catch (Exception e) {
                throw new RuntimeException("Error fetching details for appid: " + gameDto.getAppid(), e);
            }
        });
    }

    /**
     * Save detailed game information to the database.
     * @param appid   The appid of the game.
     * @param name    The name of the game.
     * @param details The detailed information of the game.
     */
    private void saveGameDetails(long appid, String name, GameDetails details) {
        Developer developer = getOrCreateDeveloper(details.getDeveloper());
        Publisher publisher = getOrCreatePublisher(details.getPublisher());
        List<Genre> genres = getOrCreateGenres(details.getGenres());
        List<Platform> platforms = getOrCreatePlatforms(details.getPlatforms());

        Game game = new Game();
        game.setAppId(appid);
        game.setTitle(name);
        game.setDeveloper(developer);
        game.setPublisher(publisher);
        game.setGenres(genres);
        game.setPlatforms(platforms);
        gameRepository.save(game);
    }

    private Developer getOrCreateDeveloper(String developerName) {
        return developerRepository.findByName(developerName)
                .orElseGet(() -> {
                    Developer developer = new Developer();
                    developer.setName(developerName);
                    return developerRepository.save(developer);
                });
    }

    private Publisher getOrCreatePublisher(String publisherName) {
        return publisherRepository.findByName(publisherName)
                .orElseGet(() -> {
                    Publisher publisher = new Publisher();
                    publisher.setName(publisherName);
                    return publisherRepository.save(publisher);
                });
    }

    private List<Genre> getOrCreateGenres(List<String> genreNames) {
        return genreNames.stream()
                .map(genreName -> genreRepository.findByName(genreName)
                        .orElseGet(() -> {
                            Genre genre = new Genre();
                            genre.setName(genreName);
                            return genreRepository.save(genre);
                        }))
                .collect(Collectors.toList());
    }

    private List<Platform> getOrCreatePlatforms(List<String> platformNames) {
        return platformNames.stream()
                .map(platformName -> platformRepository.findByName(platformName)
                        .orElseGet(() -> {
                            Platform platform = new Platform();
                            platform.setName(platformName);
                            return platformRepository.save(platform);
                        }))
                .collect(Collectors.toList());
    }
}
