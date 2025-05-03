package com.tms.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity(name = "news")
public class News {
    @Id
    @SequenceGenerator(name = "news_seq_gen", sequenceName = "news_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "news_seq_gen")
    private Long id;

    @NotNull(message = "News title cannot be null")
    @Column(name = "title")
    private String title;

    @Column(name = "image_news")
    private String imageNews;

    @Column(name = "description_news")
    private String descriptionNews;

    @Column(name = "user_id")
    private Long authorNewsId;

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
