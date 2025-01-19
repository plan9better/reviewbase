package dev.plan9better.webapi.controller;

import dev.plan9better.data.model.AppUser;
import dev.plan9better.data.repository.AppUserRepository;
import dev.plan9better.webapi.contracts.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_returnsUserId_whenUserExists() {
        LoginDto loginDto = new LoginDto("existingUser");
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("existingUser");

        when(appUserRepository.findByUsername("existingUser")).thenReturn(user);

        ResponseEntity<Object> response = loginController.Login(loginDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }

    @Test
    void login_returnsNotFound_whenUserDoesNotExist() {
        LoginDto loginDto = new LoginDto("nonExistingUser");

        when(appUserRepository.findByUsername("nonExistingUser")).thenReturn(null);

        ResponseEntity<Object> response = loginController.Login(loginDto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void register_createsNewUser_whenUserDoesNotExist() {
        LoginDto registerDto = new LoginDto("newUser");

        when(appUserRepository.findByUsername("newUser")).thenReturn(null);

        ResponseEntity<Object> response = loginController.Register(registerDto);

        assertEquals(404, response.getStatusCodeValue());
        verify(appUserRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    void register_returnsUserId_whenUserAlreadyExists() {
        LoginDto registerDto = new LoginDto("existingUser");
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("existingUser");

        when(appUserRepository.findByUsername("existingUser")).thenReturn(user);

        ResponseEntity<Object> response = loginController.Register(registerDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }
}