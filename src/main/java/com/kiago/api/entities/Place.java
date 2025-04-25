package com.kiago.api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "places")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario createdBy;


    private String name;

    private String category;

    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getCreatedBy() {
        return createdBy;
    }

    public Company getCompany() {
        return company;
    }
}
