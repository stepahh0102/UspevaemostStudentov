package com.example.untitled.service;

import com.example.untitled.model.User;
import com.example.untitled.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String password) {
        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return null;
        }
        if (userRepository.existsByUsername(username)) {
            return null;
        }
        User user = new User(username.trim(), password);
        return userRepository.save(user);
    }

    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.isPresent()
                && userOpt.get().getPassword().equals(password);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}