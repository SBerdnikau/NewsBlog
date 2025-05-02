package com.tms.service;

import com.tms.model.entity.User;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(User user) {
         return Optional.of(userRepository.save(user));
    }

    public Boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    public Boolean createUser(User user) {
        user.setIsDeleted(false);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
