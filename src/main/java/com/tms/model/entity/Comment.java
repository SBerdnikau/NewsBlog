package com.tms.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Scope("prototype")
@Component
@Entity(name = "comments")
public class Comment {
    @Id
    @SequenceGenerator(name = "comments_id_seq_gen", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "comments_id_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Comments topic cannot be null")
    @Column(name = "comment_topic")
    private String commentTopic;

    @Column(name = "description_comments")
    private String descriptionComments;

    @NotNull(message = "Author (user) ID commenter cannot be null")
    @Column(name = "author_comments")
    private Long authorComments;

    @NotNull(message = "Created date cannot be null")
    @Column(name = "created", updatable = false)
    private Timestamp created;

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
