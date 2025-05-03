package com.tms.exception;

import jakarta.validation.constraints.*;

public class RegistrationException extends Exception {
    public RegistrationException(@NotNull(message = "Login cannot be null")
                                 @NotBlank(message = "Login cannot be blank")
                                 @Size(min = 4, max = 50, message = "Login must be between 4 and 50 characters") String login,
                                 @NotNull(message = "Email cannot be null.")
                                 @Email(message = "Invalid email format.") String email,
                                 @NotNull(message = "Telephone number cannot be null")
                                 @Pattern(regexp = "[0-9]{12}", message = "Telephone number must be exactly 12 digits.")
                                 String telephoneNumber) {
    }
}
