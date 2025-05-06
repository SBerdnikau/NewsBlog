package com.tms.service;

import com.tms.exception.RegistrationException;
import com.tms.model.dto.AuthRequestDto;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.model.dto.RegistrationResponseDto;
import com.tms.model.dto.SecurityResponseDto;
import com.tms.model.dto.UserResponseDto;
import com.tms.model.entity.Role;
import com.tms.model.entity.Security;
import com.tms.model.entity.User;
import com.tms.repository.SecurityRepository;
import com.tms.repository.UserRepository;
import com.tms.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SecurityService {
    private final SecurityRepository securityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Autowired
    public SecurityService(SecurityRepository securityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.securityRepository = securityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<Security> getSecurityById(Long id) {
        if (canAccessSecurity(id)) {
            return securityRepository.findById(id);
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id: " + id);
    }


    @Transactional(rollbackFor = RegistrationException.class)
    public Optional<RegistrationResponseDto> registration(RegistrationRequestDto registrationRequestDto)  {
        if (securityRepository.existsByLogin(registrationRequestDto.getLogin())  ||
                userRepository.existsByEmailOrTelephoneNumber(registrationRequestDto.getEmail(),
                        registrationRequestDto.getTelephoneNumber() ) ) {
            try {
                throw new RegistrationException("Registration error: This (login, phone, email) already exists -> " + registrationRequestDto.getLogin(), registrationRequestDto.getEmail(), registrationRequestDto.getTelephoneNumber());
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
                .password(passwordEncoder.encode(registrationRequestDto.getPassword()))
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

    public boolean canAccessUser(Long userId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Security> securityOptional = securityRepository.findByLogin(login);
        if (securityOptional.isEmpty()) {
            return false;
        }
        Security security = securityOptional.get();
        return security.getRole().equals(Role.ADMIN) || security.getUserId().equals(userId);
    }

    public boolean canAccessSecurity(Long securityId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Security> securityOptional = securityRepository.findByLogin(login);
        if (securityOptional.isEmpty()) {
            return false;
        }
        Security security = securityOptional.get();
        return security.getRole().equals(Role.ADMIN) || security.getId().equals(securityId);
    }

    public boolean canAccessNews(Long userId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Security> securityOptional = securityRepository.findByLogin(login);
        if (securityOptional.isEmpty()) {
            return false;
        }
        Security security = securityOptional.get();
        return security.getRole().equals(Role.ADMIN) || security.getUserId().equals(userId);
    }

    public boolean canAccessComment(Long userId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Security> securityOptional = securityRepository.findByLogin(login);
        if (securityOptional.isEmpty()) {
            return false;
        }
        Security security = securityOptional.get();
        return security.getRole().equals(Role.ADMIN) || security.getUserId().equals(userId);
    }

    public Optional<String> generateToken(AuthRequestDto authRequestDto) {
        Optional<Security> securityOptional = securityRepository.findByLogin(authRequestDto.getLogin());

        if (securityOptional.isPresent() && passwordEncoder.matches(authRequestDto.getPassword(), securityOptional.get().getPassword())) {
            return jwtUtil.generateJwtToken(authRequestDto.getLogin());
        }
        return Optional.empty();
    }

}
