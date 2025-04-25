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

    @Autowired
    private PlaceService placeService;

    @GetMapping
    public List<PlaceDTO> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/{id}")
    public PlaceDTO getPlaceById(@PathVariable Long id) {
        return placeService.getPlaceById(id);
    }

    @PostMapping
    public ResponseEntity<?> createPlace(@RequestBody PlaceDTO placeDTO) {
        return placeService.createPlace(placeDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePlace(@PathVariable Long placeId, Long usuarioId) {
        placeService.deletePlace(placeId,usuarioId);
    }

    @PutMapping("/{id}")
    public Place updatePlace(@PathVariable Long id, @RequestBody Place placeDetails) {
        return placeService.updatePlace(id, placeDetails);
    }
}
