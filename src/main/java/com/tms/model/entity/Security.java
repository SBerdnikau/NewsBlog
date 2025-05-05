package com.tms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Scope("prototype")
@Entity(name = "security")
public class Security {
    @Id
    @SequenceGenerator(name = "security_seq_gen", sequenceName = "security_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "security_seq_gen")
    private Long id;

    @NotNull(message = "Login cannot be null")
    @Column(name = "login", length = 50)
    private String login;

    @Column(name = "password")
    private String password;

    @NotNull
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
