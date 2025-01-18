package dev.plan9better.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Admin extends User{
    @ManyToMany
    private List<Feedback> managedFeedbacks;
}
