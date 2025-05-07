package com.tms.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Entity(name = "comments")
public class Comment {
    @Id
    @SequenceGenerator(name = "comments_id_seq_gen", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "comments_id_seq_gen")
    private Long id;

    @NotNull(message = "Comments topic cannot be null")
    @Column(name = "comment_topic")
    private String commentTopic;

    @Column(name = "description_comments")
    private String descriptionComments;

    @NotNull
    @Column(name = "user_id")
    private Long authorCommentId;

    @NotNull
    @Column(name = "news_id")
    private Long newsId;

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
