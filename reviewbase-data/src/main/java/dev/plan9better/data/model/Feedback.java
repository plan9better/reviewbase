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
    private AppUser author;

    private String comment;

    @ManyToOne
    private AppUser admin;

    private Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review subject;

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public AppUser getAuthor() {
        return author;
    }
    public void setAuthor(AppUser author) {
        this.author = author;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public AppUser getAdmin() {
        return admin;
    }
    public void setAdmin(AppUser admin) {
        this.admin = admin;
    }
    public Verdict getVerdict() {
        return verdict;
    }
    public void setVerdict(Verdict verdict) {
        this.verdict = verdict;
    }
    public Review getSubject() {
        return subject;
    }
    public void setSubject(Review subject) {
        this.subject = subject;
    }
}