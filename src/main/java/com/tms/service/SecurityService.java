package com.tms.service;

import com.tms.exception.LoginUsedException;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.model.entity.Role;
import com.tms.model.entity.Security;
import com.tms.model.entity.User;
import com.tms.repository.SecurityRepository;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SecurityService {
    private final SecurityRepository securityRepository;
    private final UserRepository userRepository;
    private User user;
    private Security security;

    @Autowired
    public SecurityService(SecurityRepository securityRepository, UserRepository userRepository, User user, Security security) {
        this.securityRepository = securityRepository;
        this.userRepository = userRepository;
        this.user = user;
        this.security = security;
    }

    public Optional<Security> getSecurityById(Long id) {
       return securityRepository.findById(id);
    }

    @Transactional(rollbackFor = LoginUsedException.class)
    public Optional<User> registration(RegistrationRequestDto registrationRequestDto)  {
        if (securityRepository.existsByLogin(registrationRequestDto.getLogin())) {
            try {
                throw new LoginUsedException(registrationRequestDto.getLogin());
            } catch (LoginUsedException e) {
                throw new RuntimeException(e);
            }
        }

        user.setUserName(registrationRequestDto.getUserName());
        user.setSecondName(registrationRequestDto.getSecondName());
        user.setEmail(registrationRequestDto.getEmail());
        user.setTelephoneNumber(registrationRequestDto.getTelephoneNumber());
        user.setIsDeleted(false);

        User userUpdated = userRepository.save(user);

        security.setLogin(registrationRequestDto.getLogin());
        security.setPassword(registrationRequestDto.getPassword());
        security.setUserId(userUpdated.getId());
        security.setRole(Role.USER);

        securityRepository.save(security);

        return  userRepository.findById(userUpdated.getId());
    }
}
