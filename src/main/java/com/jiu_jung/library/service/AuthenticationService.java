package com.jiu_jung.library.service;

import com.jiu_jung.library.domain.User;
import com.jiu_jung.library.dto.LoginRequest;
import com.jiu_jung.library.dto.LoginResponse;
import com.jiu_jung.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<LoginResponse> login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            String token = "TOKEN_" + user.get().getUser_id();
            return Optional.of(new LoginResponse(token, "Login successful"));
        }
        return Optional.empty();
    }
}
