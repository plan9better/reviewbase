package dev.plan9better.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Admin extends AppUser {
    @ManyToMany
    private List<Feedback> managedFeedbacks;

    public List<Feedback> getManagedFeedbacks() {
        return managedFeedbacks;
    }
}
