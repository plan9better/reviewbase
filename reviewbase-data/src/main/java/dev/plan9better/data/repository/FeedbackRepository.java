package dev.plan9better.data.repository;

import dev.plan9better.data.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
