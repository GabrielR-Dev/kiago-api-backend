package com.kiago.api.controller;

import com.kiago.api.entities.Photo;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @GetMapping("/{id}")
    public Optional<Photo> getPhotoById(@PathVariable Long id) {
        return photoService.getPhotoById(id);
    }

    @PostMapping
    public Photo createPhoto(@RequestBody Photo photo) {
        return photoService.createPhoto(photo);
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
    }

    @PutMapping("/{id}")
    public Photo updatePhoto(@PathVariable Long id, @RequestBody Photo photoDetails) {
        return photoService.updatePhoto(id, photoDetails);
    }
}
