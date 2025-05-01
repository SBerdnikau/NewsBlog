package com.tms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Scope("prototype")
@Component
@Entity(name = "users")
public class Users {
    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq_gen")
    private Long id;

    @NotNull
    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Column(name = "second_name")
    private String secondName;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "telephone_number")
    private String telephoneNumber;

    @NotNull
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @NotNull
    @JsonIgnore
    @Column(name = "created", updatable = false)
    private Timestamp created;

    @JsonIgnore
    @Column(name = "updated")
    private Timestamp updated;

    protected void onCreate() {
        created = new Timestamp(System.currentTimeMillis());
        updated = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Timestamp(System.currentTimeMillis());
    }
}
