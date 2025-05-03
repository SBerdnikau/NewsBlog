package com.tms.service;

import com.tms.exception.LoginUsedException;
import com.tms.exception.RegistrationException;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.model.dto.RegistrationResponseDto;
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


    @Autowired
    public SecurityService(SecurityRepository securityRepository, UserRepository userRepository) {
        this.securityRepository = securityRepository;
        this.userRepository = userRepository;
    }

    public Optional<Security> getSecurityById(Long id) {
       return securityRepository.findById(id);
    }

    @Transactional(rollbackFor = LoginUsedException.class)
    public Optional<RegistrationResponseDto> registration(RegistrationRequestDto registrationRequestDto)  {
        if (securityRepository.existsByLogin(registrationRequestDto.getLogin())  ||
                userRepository.existsByEmailOrTelephoneNumber(registrationRequestDto.getEmail(),
                        registrationRequestDto.getTelephoneNumber() ) ) {
            try {
                throw new RegistrationException("Registration error: This (login, phone, email) already exists");
            } catch (RegistrationException e) {
                throw new RuntimeException(e);
            }
        }

        User user = User.builder()
                .userName(registrationRequestDto.getUserName())
                .secondName(registrationRequestDto.getSecondName())
                .email(registrationRequestDto.getEmail())
                .telephoneNumber(registrationRequestDto.getTelephoneNumber())
                .build();
        User userRegistered = userRepository.save(user);

        Security security = Security.builder()
                .login(registrationRequestDto.getLogin())
                .password(registrationRequestDto.getPassword())
                .userId(userRegistered.getId())
                .role(Role.USER)
                .build();
        securityRepository.save(security);

        RegistrationResponseDto registrationResponseDto = RegistrationResponseDto.builder()
                .userName(userRegistered.getUserName())
                .secondName(userRegistered.getSecondName())
                .email(userRegistered.getEmail())
                .telephoneNumber(userRegistered.getTelephoneNumber())
                .build();

        return  Optional.of(registrationResponseDto);
    }
}
