package com.kiago.api.controller;

import com.kiago.api.dtos.PhotoDTO;
import com.kiago.api.entities.Photo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.kiago.api.service.PhotoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/photos")
@Tag(name = "Users", description = "Peticiones para las Fotos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @GetMapping
    @Operation(summary = "Listar todas las fotos")
    public ResponseEntity<?> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint para actualizar lugares usuarios",description = "Este endpoin edita Lugares con el id del Usuario conectado. " +
            "Necesita token")
    public ResponseEntity<?> getPhotoById(@PathVariable Long id) {
        return photoService.getPhotoById(id);
    }

    @PostMapping("/{idPlace}")
    @Operation(summary = "Endpoint para agregar fotos a un Lugar",description = "Este endpoin Necesita el ID de Lugar " +
            "Necesita token")
    public ResponseEntity<?> addPhoto(@Valid @RequestBody PhotoDTO photoDTO,@PathVariable Long idPlace) {
        return photoService.addPhoto(photoDTO,idPlace);
    }

    @GetMapping("/place/{idPlace}")
    @Operation(summary = "Endpoint trae todos las fotos de un Lugar",description = "Este endpoin necesita el ID del Lugar para traer todas sus fotos " +
            "Necesita token")
    public ResponseEntity<?> getPhotoByIdPlace(@PathVariable Long idPlace) {
        return photoService.getPhotoByIdPlace(idPlace);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint para eliminar la foto",description = "Este endpoin elimina la Foto del Lugar. Se le pasa el ID de la foto" +
            "Necesita token")
    public ResponseEntity<?> deletePhoto(@PathVariable Long id) {
        return photoService.deletePhoto(id);
    }
}
