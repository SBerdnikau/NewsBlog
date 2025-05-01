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
@Entity(name = "news")
public class News {
    @Id
    @SequenceGenerator(name = "news_seq_gen", sequenceName = "news_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "news_seq_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Mews title cannot be null")
    @Column(name = "title")
    private String title;

    @Column(name = "image_news")
    private String imageNews;

    @Column(name = "description_news")
    private String descriptionNews;

    @NotNull(message = "Author (user) ID cannot be null")
    @Column(name = "author_news")
    private Long authorNews;

    @NotNull
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
