package com.kiago.api.controller;

import com.kiago.api.dtos.PlaceDTO;
import com.kiago.api.entities.Place;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kiago.api.service.PlaceService;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@Tag(name = "Users", description = "Peticiones para Usuarios")
@Tag(name = "Users", description = "Peticiones para los Lugares")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    @Operation(summary = "Endpoint para traer todos los lugares",description = "No necesita token")
    public ResponseEntity<?> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar todos los Lugares")
    public ResponseEntity<?> getPlaceById(@PathVariable Long id) {
        return placeService.getPlaceById(id);
    }

    @PostMapping
    @Operation(summary = "Endpoint para crear Lugares", description = "Este endpoin crea Lugares con el id del Usuario conectado. " +
            "Necesita token")
    public ResponseEntity<?> createPlace(@RequestBody PlaceDTO placeDTO) {
        return placeService.createPlace(placeDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Endpoint para actualizar el Lugar con el ID de la URL",description = "Este endpoin edita Lugares con el id del Usuario conectado. " +
            "Necesita token")
    public ResponseEntity<?> updatePlace(@PathVariable Long id, @RequestBody PlaceDTO placeDTO) {
        return placeService.updatePlace(id, placeDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina el lugar con el ID que se le pasa en la URL",description = "Este endpoin no puede eliminar lugares de otros usuarios")
    public ResponseEntity<?> deletePlace(@PathVariable Long id) {
        return placeService.deletePlace(id);
    }
}
