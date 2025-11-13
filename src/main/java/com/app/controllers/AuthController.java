package com.app.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.payloads.LoginCredentials;
import com.app.payloads.UserDTO;
import com.app.security.JWTUtil;
import com.app.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth") // ✅ Change this if your security scheme name is different
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerHandler(@Valid @RequestBody UserDTO user) {
        // Encode password
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);

        // Register user
        UserDTO userDTO = userService.registerUser(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(userDTO.getEmail());

        return new ResponseEntity<>(Collections.singletonMap("jwt-token", token), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginHandler(@Valid @RequestBody LoginCredentials credentials) {
        // Authenticate user
        UsernamePasswordAuthenticationToken authCredentials =
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());

        authenticationManager.authenticate(authCredentials);

        // Generate JWT token
        String token = jwtUtil.generateToken(credentials.getEmail());

        return ResponseEntity.ok(Collections.singletonMap("jwt-token", token));
    }
}
