package dev.plan9better.webapi.controller;

import dev.plan9better.data.model.AppUser;
import dev.plan9better.data.repository.AppUserRepository;
import dev.plan9better.webapi.contracts.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin("*")
public class LoginController {
    private final AppUserRepository appUserRepository;
    public LoginController(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    @PostMapping
    public ResponseEntity<Object> Login(@RequestBody LoginDto login){
        AppUser user = appUserRepository.findByUsername(login.username());
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getId());
    }
    @PostMapping("/register")
    public ResponseEntity<Object> Register(@RequestBody LoginDto register){
        AppUser user = appUserRepository.findByUsername(register.username());
        if(user == null){
            AppUser newUser = new AppUser();
            newUser.setUsername(register.username());
            appUserRepository.save(newUser);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getId());
    }
}
