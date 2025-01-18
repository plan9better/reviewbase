package dev.plan9better.data.repository;

import dev.plan9better.data.model.Developer;
import dev.plan9better.data.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByName(String name);
}
