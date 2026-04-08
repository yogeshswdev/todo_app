package com.example.todoapp.service;

import com.example.todoapp.model.AppUser;
import com.example.todoapp.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String normalizeUsername(String username) {
        if (username == null) {
            return "";
        }
        return username.trim().toLowerCase();
    }

    public Optional<AppUser> register(String username, String rawPassword) {
        String normalizedUsername = normalizeUsername(username);

        if (normalizedUsername.isEmpty() || rawPassword == null || rawPassword.trim().isEmpty()) {
            return Optional.empty();
        }

        if ("guest".equals(normalizedUsername) || appUserRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
            return Optional.empty();
        }

        AppUser user = new AppUser();
        user.setUsername(normalizedUsername);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));

        return Optional.of(appUserRepository.save(user));
    }

    public boolean login(String username, String rawPassword) {
        String normalizedUsername = normalizeUsername(username);

        if (normalizedUsername.isEmpty() || rawPassword == null || rawPassword.trim().isEmpty()) {
            return false;
        }

        return appUserRepository.findByUsernameIgnoreCase(normalizedUsername)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPasswordHash()))
                .orElse(false);
    }

    public boolean resetPassword(String username, String newPassword) {
        String normalizedUsername = normalizeUsername(username);

        if (normalizedUsername.isEmpty() || "guest".equals(normalizedUsername)
                || newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }

        Optional<AppUser> existingUser = appUserRepository.findByUsernameIgnoreCase(normalizedUsername);
        if (existingUser.isEmpty()) {
            return false;
        }

        AppUser user = existingUser.get();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        appUserRepository.save(user);
        return true;
    }
}
