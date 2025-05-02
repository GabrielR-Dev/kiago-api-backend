package com.kiago.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Debe contener un nombre")
    private String name;

    private String description;


    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Place> places = new ArrayList<>();

    //Constructores


    public Company() {
    }

    public Company(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Company(Long id, String name, String description, List<Place> places) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.places = places;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Place> getLugares() {
        return places;
    }

    public void setLugares(List<Place> lugares) {
        this.places = lugares;
    }

    public List<Place> getPlaces() {
        return places;
    }


}
