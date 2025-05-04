package com.tms.service;

import com.tms.exception.RegistrationException;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.model.dto.RegistrationResponseDto;
import com.tms.model.entity.Role;
import com.tms.model.entity.Security;
import com.tms.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegistrationService {

    private final SecurityService securityService;
    private final UserService userService;

    @Autowired
    public RegistrationService(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }



}
