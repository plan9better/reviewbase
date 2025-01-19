package dev.plan9better.data.repository;

import dev.plan9better.data.model.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
    Reviewer findBySourceId(String sourceId);
}
