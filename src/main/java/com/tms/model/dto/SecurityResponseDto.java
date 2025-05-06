package com.tms.model.dto;

import com.tms.model.entity.Role;
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
public class SecurityResponseDto {

    String login;
    String password;
    Long userId;
    Role role;

}
