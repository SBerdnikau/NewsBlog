package com.tms.model.dto;

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
public class RegistrationResponseDto {
    private String userName;

    private String secondName;

    private String email;

    private String telephoneNumber;
}
