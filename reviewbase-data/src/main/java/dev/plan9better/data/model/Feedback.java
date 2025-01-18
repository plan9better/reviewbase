package dev.plan9better.data.model;

import dev.plan9better.data.model.enums.Verdict;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    private String comment;

    @ManyToOne
    private User admin;

    private Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review subject;
}