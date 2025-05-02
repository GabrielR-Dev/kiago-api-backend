package com.kiago.api.controller;

import com.kiago.api.dtos.PhotoDTO;
import com.kiago.api.entities.Photo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.kiago.api.service.PhotoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @GetMapping
    public ResponseEntity<?> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPhotoById(@PathVariable Long id) {
        return photoService.getPhotoById(id);
    }

    @PostMapping
    public ResponseEntity<?> createPhoto(@Valid @RequestBody PhotoDTO photoDTO) {
        return photoService.createPhoto(photoDTO);
    }

    @GetMapping("/place/{place}")
    public ResponseEntity<?> getPlaceByIdPlace(@PathVariable Long idPlace) {
        return photoService.getPhotoByIdPlace(idPlace);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhoto(@PathVariable Long id) {
        return photoService.deletePhoto(id);
    }
}
