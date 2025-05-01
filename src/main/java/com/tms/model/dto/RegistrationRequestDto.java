package com.tms.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
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
    @NotBlank(message = "Login cannot be blank")
    @Size(min = 4, max = 50, message = "Login must be between 4 and 50 characters")
    private String login;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    private String password;
}
