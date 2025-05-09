package com.example.subscription.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.subscription.dao.UserRepository;
import com.example.subscription.dto.UserDto;
import com.example.subscription.exceptions.UserNotFoundException;
import com.example.subscription.models.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    public boolean createUser(User user) {
        userRepository.save(user);
        return true;
    }

    public User getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return user;
    }

    public boolean updateUser(UUID id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(UUID id) {
        userRepository.deleteById(id);
        return true;
    }
    
}
