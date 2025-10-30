package com.attedance_sys.demo.services;

import com.attedance_sys.demo.Repositories.UserReository;
import com.attedance_sys.demo.entity.AuthenticationResponse;
import com.attedance_sys.demo.entity.User;
import com.attedance_sys.demo.exceptions.UserNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserReository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserReository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // ---------------------- REGISTER ----------------------
    public AuthenticationResponse register(User request) {
        // Vérifier si l'utilisateur existe déjà
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exists");
        }

        // Créer un nouvel utilisateur
        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // encoder le mot de passe
        user.setEmail(request.getEmail());
        user.setRole(request.getRole()); // <-- rôle dynamique depuis le front

        // Sauvegarder l'utilisateur dans la base
        user = repository.save(user);

        // Générer un token JWT
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token, "User registration successful");
    }

    // ---------------------- AUTHENTICATE ----------------------
    public AuthenticationResponse authenticate(User request) {
        // Vérifier le mot de passe (Spring Security)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Trouver l'utilisateur par username
        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        // Générer le token JWT
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token, "User login was successful");
    }
}
