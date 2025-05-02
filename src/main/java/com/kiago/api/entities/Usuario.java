package com.kiago.api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "createdBy")
    private List<Place> createdPlaces = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Usuario(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Usuario(Long id, String name, String email, String password, List<Place> createdPlaces, Company company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdPlaces = createdPlaces;
        this.company = company;
    }

    public Usuario(Long id, String name, String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt, List<Place> createdPlaces, Company company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdPlaces = createdPlaces;
        this.company = company;
    }

    public Usuario() {
    }


    public Long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Place> getCreatedPlaces() {
        return createdPlaces;
    }
}
