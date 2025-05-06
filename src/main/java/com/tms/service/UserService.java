package com.tms.service;

import com.tms.model.dto.UserResponseDto;
import com.tms.model.entity.User;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Autowired
    public UserService(UserRepository userRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
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
        if (securityService.canAccessUser(id)){
            return userRepository.findById(id);
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + id);
    }

    public Optional<UserResponseDto> updateUser(User user) {
        if(securityService.canAccessUser(user.getId())) {
            return Optional.of(userRepository.save(user))
                    .map(userRequestDto -> UserResponseDto.builder()
                            .userName(userRequestDto.getUserName())
                            .secondName(userRequestDto.getSecondName())
                            .email(userRequestDto.getEmail())
                            .telephoneNumber(userRequestDto.getTelephoneNumber())
                            .build());
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + user.getId());
    }

    public Boolean deleteUser(Long id) {
        if(securityService.canAccessUser(id)) {
            userRepository.deleteById(id);
            return !userRepository.existsById(id);
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + id);
    }

}
