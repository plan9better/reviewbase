package dev.plan9better.data.repository;

import dev.plan9better.data.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsByAppId(Long appId);
    List<Game> findByTitleContaining(String query);
}
