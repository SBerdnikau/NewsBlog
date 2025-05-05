package com.tms.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Scope("prototype")
@Component
public class RegistrationRequestDto {

    @NotNull(message = "User name cannot be null")
    private String userName;

    @NotNull(message = "Second name cannot be null")
    private String secondName;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotNull(message = "Telephone number cannot be null")
    @Pattern(regexp = "[0-9]{12}", message = "Telephone number must be exactly 12 digits.")
    private String telephoneNumber;

    @NotNull(message = "Login cannot be null")
    private String login;

    @NotNull(message = "Password cannot be null")
    private String password;
}
