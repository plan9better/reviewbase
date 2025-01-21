package dev.plan9better.data.repository;

import dev.plan9better.data.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findBySessionToken(UUID sessionToken);
}
