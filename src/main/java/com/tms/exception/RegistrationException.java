package com.tms.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegistrationException extends Exception {
    public RegistrationException(@NotNull(message = "Login cannot be null") @NotBlank(message = "Login cannot be blank") @Size(min = 4, max = 50, message = "Login must be between 4 and 50 characters") String login) {
    }
}
