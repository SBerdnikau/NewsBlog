package com.tms.exception;


import jakarta.validation.constraints.NotNull;

public class SecurityLoginUsedException extends Exception {
    public SecurityLoginUsedException(@NotNull(message = "Login cannot be null") String s) {
    }
}
