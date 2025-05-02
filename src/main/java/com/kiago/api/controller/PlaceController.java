package com.kiago.api.controller;

import com.kiago.api.dtos.PlaceDTO;
import com.kiago.api.entities.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kiago.api.service.PlaceService;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaceById(@PathVariable Long id) {
        return placeService.getPlaceById(id);
    }

    @PostMapping
    public ResponseEntity<?> createPlace(@RequestBody PlaceDTO placeDTO) {
        return placeService.createPlace(placeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlace(@PathVariable Long id, @RequestBody PlaceDTO placeDTO) {
        return placeService.updatePlace(id, placeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable Long id) {
        return placeService.deletePlace(id);
    }
}
