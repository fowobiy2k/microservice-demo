package com.microservice.authentication.service;

import com.microservice.authentication.entity.UserCredential;
import com.microservice.authentication.repository.UserCredentialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String addUser(UserCredential user) {

        log.info("encrypting user password");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        log.info("user saved successfully");

        return "user saved successfully";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
