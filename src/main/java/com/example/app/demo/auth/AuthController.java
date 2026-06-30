package com.example.app.demo.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.app.demo.users.UserRepository;
import com.example.app.demo.users.UserResponse;
import com.example.app.demo.users.UserRole;
import com.example.app.demo.security.JwtUtil;
import com.example.app.demo.users.UserEntity;
import com.example.app.demo.users.UserLoginRequest;
import com.example.app.demo.users.UserRegisterRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository repository, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRegisterRequest request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);

        UserEntity saved = repository.save(user);

        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setEmail(saved.getEmail());

        return response;
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginRequest request) {
        UserEntity user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return jwtUtil.generateToken(user);
    }
}
