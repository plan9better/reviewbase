package dev.plan9better.webapi.service;

import dev.plan9better.data.model.AppUser;
import dev.plan9better.data.model.Session;
import dev.plan9better.data.repository.AppUserRepository;
import dev.plan9better.data.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final AppUserRepository appUserRepository;

    public SessionService(SessionRepository sessionRepository, AppUserRepository appUserRepository) {
        this.sessionRepository = sessionRepository;
        this.appUserRepository = appUserRepository;
    }

    public UUID newMonthSession(AppUser user){
        Session session = new Session();
        session.setUser(user);
        session.setSessionToken(UUID.randomUUID());
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusMonths(1));
        sessionRepository.save(session);

        return session.getSessionToken();
    }

    public AppUser getUserWithSession(String sessionToken){
        Session session = sessionRepository.findBySessionToken(UUID.fromString(sessionToken));
        if(session != null){
            return session.getUser();
        }
        throw new RuntimeException("User not found");
    }
}
