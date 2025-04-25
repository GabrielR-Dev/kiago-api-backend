package com.kiago.api.entities;

import jakarta.persistence.*;
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

    private String nombre;


    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Place> lugares = new ArrayList<>();

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Place> getLugares() {
        return lugares;
    }

    public void setLugares(List<Place> lugares) {
        this.lugares = lugares;
    }
}
