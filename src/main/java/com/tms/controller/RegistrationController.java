package com.tms.controller;

import com.tms.exception.RegistrationException;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.model.dto.RegistrationResponseDto;
import com.tms.service.SecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final SecurityService securityService;

    @Autowired
    public RegistrationController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDto > registration(@RequestBody @Valid RegistrationRequestDto requestDto,
                                             BindingResult bindingResult) throws RegistrationException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<RegistrationResponseDto> userRegistered = securityService.registration(requestDto);
        if (userRegistered.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userRegistered.get(), HttpStatus.CREATED);
    }

}
