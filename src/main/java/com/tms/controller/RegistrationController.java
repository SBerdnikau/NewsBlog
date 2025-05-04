package com.tms.controller;

import com.tms.model.dto.RegistrationRequestDto;
import com.tms.model.dto.RegistrationResponseDto;
import com.tms.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Tag(name = "Security Controller", description = "Registration Management")
public class RegistrationController {

    private final SecurityService securityService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    public RegistrationController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Operation(summary = "User registration", description = "Registers a new user in the system")
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Incorrect request data")
    @ApiResponse(responseCode = "409", description = "Conflict: user already exists")
    @PostMapping
    public ResponseEntity<RegistrationResponseDto > registration(@RequestBody @Valid RegistrationRequestDto requestDto,
                                             BindingResult bindingResult) {
        logger.info("Received registration request for user: {}", requestDto.getLogin());
        if (bindingResult.hasErrors()) {
            logger.warn("Login '{}' is already in use.", requestDto.getLogin());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<RegistrationResponseDto> userRegistered = securityService.registration(requestDto);
        if (userRegistered.isEmpty()) {
            logger.info("Conflict: User not registered {}", requestDto.getLogin());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("User registered successfully: {}", userRegistered.get());
        return new ResponseEntity<>(userRegistered.get(), HttpStatus.CREATED);
    }

}
