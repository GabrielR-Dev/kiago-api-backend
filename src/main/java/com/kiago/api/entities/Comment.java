package com.kiago.api.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private Integer rating;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Comment(Long id, Place place, Usuario user, String comment, Integer rating) {
        this.place = place;
        this.user = user;
        this.comment = comment;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }
}
