package com.example.todoapp.service;

import com.example.todoapp.model.AppUser;
import com.example.todoapp.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AuthService authService;

    private AppUser savedUser;

    @BeforeEach
    void setUp() {
        savedUser = new AppUser();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
    }

    @Test
    void register_ShouldCreateUser_WhenValidInput() {
        when(appUserRepository.existsByUsernameIgnoreCase("testuser")).thenReturn(false);
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<AppUser> result = authService.register("TestUser", "password123");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
        assertNotNull(result.get().getPasswordHash());
        assertNotEquals("password123", result.get().getPasswordHash());
    }

    @Test
    void register_ShouldFail_WhenGuestUsername() {
        Optional<AppUser> result = authService.register("guest", "password123");

        assertTrue(result.isEmpty());
    }

    @Test
    void login_ShouldSucceed_WithCorrectPassword() {
        when(appUserRepository.existsByUsernameIgnoreCase("testuser")).thenReturn(false);
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppUser created = authService.register("testuser", "password123").orElseThrow();
        when(appUserRepository.findByUsernameIgnoreCase("testuser")).thenReturn(Optional.of(created));

        boolean result = authService.login("testuser", "password123");

        assertTrue(result);
    }

    @Test
    void login_ShouldFail_WithWrongPassword() {
        when(appUserRepository.existsByUsernameIgnoreCase("testuser")).thenReturn(false);
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppUser created = authService.register("testuser", "password123").orElseThrow();
        when(appUserRepository.findByUsernameIgnoreCase("testuser")).thenReturn(Optional.of(created));

        boolean result = authService.login("testuser", "wrong-password");

        assertFalse(result);
    }

    @Test
    void resetPassword_ShouldUpdatePassword_WhenUserExists() {
        when(appUserRepository.existsByUsernameIgnoreCase("testuser")).thenReturn(false);
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppUser created = authService.register("testuser", "password123").orElseThrow();
        when(appUserRepository.findByUsernameIgnoreCase("testuser")).thenReturn(Optional.of(created));

        boolean resetResult = authService.resetPassword("testuser", "new-password");
        boolean loginWithOldPassword = authService.login("testuser", "password123");
        boolean loginWithNewPassword = authService.login("testuser", "new-password");

        assertTrue(resetResult);
        assertFalse(loginWithOldPassword);
        assertTrue(loginWithNewPassword);
    }

    @Test
    void resetPassword_ShouldFail_WhenUserDoesNotExist() {
        when(appUserRepository.findByUsernameIgnoreCase("missinguser")).thenReturn(Optional.empty());

        boolean result = authService.resetPassword("missinguser", "new-password");

        assertFalse(result);
    }
}
