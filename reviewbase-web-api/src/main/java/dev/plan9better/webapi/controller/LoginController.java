package dev.plan9better.webapi.controller;

import dev.plan9better.data.model.AppUser;
import dev.plan9better.data.model.Session;
import dev.plan9better.data.repository.AppUserRepository;
import dev.plan9better.data.repository.SessionRepository;
import dev.plan9better.webapi.contracts.LoginDto;
import dev.plan9better.webapi.contracts.RegisterDto;
import dev.plan9better.webapi.contracts.SessionDto;
import dev.plan9better.webapi.contracts.UserDetailsDto;
import dev.plan9better.webapi.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin("*")
public class LoginController {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SessionService sessionService;
    public LoginController(AppUserRepository appUserRepository, BCryptPasswordEncoder passwordEncoder, SessionRepository sessionRepository, SessionService sessionService){
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<Object> Login(@RequestBody LoginDto login){
        AppUser user = appUserRepository.findByEmail(login.email());
        if(user == null){
            return ResponseEntity.badRequest().body("Wrong email or password");
        }
        if(passwordEncoder.matches(login.password(), user.getPassword())){
            return ResponseEntity.ok(sessionService.newMonthSession(user));
        }
        return ResponseEntity.badRequest().body("Wrong email or password");
    }

    @PostMapping("/register")
    public ResponseEntity<Object> Register(@RequestBody RegisterDto register){
        AppUser user = appUserRepository.findByEmail(register.email());
        if(user != null){
            return ResponseEntity.badRequest().body("User with this email already exists.");
        }
        AppUser newUser = new AppUser();
        newUser.setUsername(register.username());
        newUser.setEmail(register.email());

        String passwordHash = passwordEncoder.encode(register.password());
        newUser.setPassword(passwordHash);
        appUserRepository.save(newUser);

        return ResponseEntity.ok(sessionService.newMonthSession(newUser));
    }

    @PostMapping("/validateSession")
    public ResponseEntity<Object> validateSession(@RequestBody SessionDto session){
        try{
            AppUser user = sessionService.getUserWithSession(session.sessionToken());
            UserDetailsDto userDetails = new UserDetailsDto();
            userDetails.setEmail(user.getEmail());
            userDetails.setUsername(user.getUsername());
            return ResponseEntity.ok(userDetails);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
