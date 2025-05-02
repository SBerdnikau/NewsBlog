package com.tms.model.dto;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Scope("prototype")
@Component
public class RegistrationResponseDto {
    private String userName;

    private String secondName;

    private String email;

    private String telephoneNumber;

    private String login;

    private String password;
}
