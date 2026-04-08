package com.example.todoapp.controller;

import com.example.todoapp.model.AppUser;
import com.example.todoapp.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody AuthRequest request, HttpSession session) {
        Optional<AppUser> registered = authService.register(request.username(), request.password());

        if (registered.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(response(false, "Registration failed. Username may exist or input is invalid.", null));
        }

        session.setAttribute("username", registered.get().getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response(true, "Registration successful", registered.get().getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest request, HttpSession session) {
        boolean success = authService.login(request.username(), request.password());

        if (!success) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response(false, "Invalid username or password", null));
        }

        String username = authService.normalizeUsername(request.username());
        session.setAttribute("username", username);
        return ResponseEntity.ok(response(true, "Login successful", username));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody AuthRequest request) {
        boolean success = authService.resetPassword(request.username(), request.password());

        if (!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response(false, "Password reset failed. Check username and new password.", null));
        }

        return ResponseEntity
                .ok(response(true, "Password reset successful. Please login with your new password.", null));
    }

    @PostMapping("/guest")
    public ResponseEntity<Map<String, Object>> continueAsGuest(HttpSession session) {
        session.setAttribute("username", "guest");
        return ResponseEntity.ok(response(true, "Continuing as guest", "guest"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(HttpSession session) {
        Object usernameObj = session.getAttribute("username");
        String username = usernameObj == null ? null : String.valueOf(usernameObj);

        if (username == null || username.isBlank()) {
            return ResponseEntity.ok(response(false, "Not logged in", null));
        }

        return ResponseEntity.ok(response(true, "Authenticated", username));
    }

    private Map<String, Object> response(boolean success, String message, String username) {
        Map<String, Object> body = new HashMap<>();
        body.put("success", success);
        body.put("message", message);
        body.put("username", username);
        return body;
    }

    public record AuthRequest(String username, String password) {
    }
}
