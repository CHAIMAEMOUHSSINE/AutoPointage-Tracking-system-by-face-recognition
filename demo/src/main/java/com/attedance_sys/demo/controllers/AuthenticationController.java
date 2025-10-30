package com.attedance_sys.demo.controllers;

import com.attedance_sys.demo.entity.AuthenticationResponse;
import com.attedance_sys.demo.entity.User;
import com.attedance_sys.demo.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") //
public class AuthenticationController {
    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User user
    ){
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            AuthenticationResponse response = authService.authenticate(user);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            // Return a more generic error message to avoid leaking information
            String errorMessage = "Invalid username or password";
            return ResponseEntity.status(HttpStatus
                    .UNAUTHORIZED).body(errorMessage);
        }
    }
}
