package com.jiu_jung.library.service;

import com.jiu_jung.library.domain.User;
import com.jiu_jung.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> addUser(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return Optional.empty(); // 이메일 중복 시 실패 반환
        }
        return Optional.of(userRepository.save(user));
    }



    public Optional<User> updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setEmail(updatedUser.getEmail());
                    return userRepository.save(existingUser);
                });
    }

    public boolean deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
