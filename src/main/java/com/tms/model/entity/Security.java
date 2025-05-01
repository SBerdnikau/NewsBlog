package com.tms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Scope("prototype")
@Component
@Entity(name = "security")
public class Security {
    @Id
    @SequenceGenerator(name = "security_seq_gen", sequenceName = "security_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "security_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Login cannot be null")
    @NotBlank(message = "Login cannot be blank")
    @Size(min = 4, max = 50, message = "Login must be between 4 and 50 characters")
    @Column(name = "login", length = 50)
    private String login;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    @Column(name = "password")
    private String password;

    @NotNull(message = "User ID cannot be null")
    @Column(name = "user_id")
    private Long userId;

    @NotNull(message = "Role cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;

    @NotNull(message = "Created date cannot be null")
    @JsonIgnore
    @Column(name = "created", updatable = false)
    private Timestamp created;

    @JsonIgnore
    @Column(name = "updated")
    private Timestamp updated;

    @PrePersist
    protected void onCreate() {
        created = new Timestamp(System.currentTimeMillis());
        updated = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Timestamp(System.currentTimeMillis());
    }
}
