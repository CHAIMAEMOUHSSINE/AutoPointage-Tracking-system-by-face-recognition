package com.attedance_sys.demo.services;

import com.attedance_sys.demo.Repositories.UserReository;
import com.attedance_sys.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserReository repository;

    public UserDetailsServiceImp(UserReository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Chercher l'utilisateur dans la base
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convertir en UserDetails Spring en utilisant le rôle réel depuis la DB
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name()) // HR ou Employee
                .build();
    }
}
