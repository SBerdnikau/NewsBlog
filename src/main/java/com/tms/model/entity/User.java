package com.tms.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Scope("prototype")
@Entity(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq_gen")
    private Long id;

    @NotNull(message = "User name cannot be null")
    @Column(name = "user_name")
    private String userName;

    @NotNull(message = "Second name cannot be null")
    @Column(name = "second_name")
    private String secondName;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotNull(message = "Telephone number cannot be null")
    @Pattern(regexp = "[0-9]{12}", message = "Telephone number must be exactly 12 digits.")
    @Column(name = "telephone_number")
    private String telephoneNumber;

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
