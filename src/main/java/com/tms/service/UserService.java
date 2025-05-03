package com.tms.service;

import com.tms.model.dto.UserResponseDto;
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

    public Optional<List<UserResponseDto>> getAllUsers() {
        return Optional.of(userRepository.findAll().stream()
                .map(user -> UserResponseDto.builder()
                        .userName(user.getUserName())
                        .secondName(user.getSecondName())
                        .email(user.getEmail())
                        .telephoneNumber(user.getTelephoneNumber())
                        .build()
                ).toList());
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserResponseDto> updateUser(User user) {
         return Optional.of(userRepository.save(user))
                 .map(userRequestDto -> UserResponseDto.builder()
                         .userName(userRequestDto.getUserName())
                         .secondName(userRequestDto.getSecondName())
                         .email(userRequestDto.getEmail())
                         .telephoneNumber(userRequestDto.getTelephoneNumber())
                         .build());
    }

    public Boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

}
