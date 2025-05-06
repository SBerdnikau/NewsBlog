package com.tms.controller;

import com.tms.model.dto.AuthRequestDto;
import com.tms.model.dto.AuthResponseDto;
import com.tms.model.dto.SecurityResponseDto;
import com.tms.model.entity.Security;
import com.tms.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/security")
@Tag(name = "Security Controller", description = "Security Management")
public class SecurityController {
    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Operation(summary = "User Security Data", description = "Return security by ID")
    @ApiResponse(responseCode = "200", description = "Data found successfully")
    @ApiResponse(responseCode = "404", description = "Data not found")
    @GetMapping("/{id}")
    public ResponseEntity<Security> getSecurityById(@PathVariable("id") @Parameter(name = "id") Long id) {
        Optional<Security> security = securityService.getSecurityById(id);
        if (security.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(security.get(), HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponseDto> generateToken(@RequestBody AuthRequestDto authRequestDto){
        Optional<String> token = securityService.generateToken(authRequestDto);
        if (token.isPresent()) {
            return new ResponseEntity<>(new AuthResponseDto(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
