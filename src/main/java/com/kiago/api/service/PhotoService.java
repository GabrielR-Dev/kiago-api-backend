package com.kiago.api.service;

import com.kiago.api.entities.Photo;
import com.kiago.api.repositories.IPhotoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private IPhotoRepository photoRepository;
    private final ModelMapper modelMapper;

    public PhotoService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    public Optional<Photo> getPhotoById(Long id) {
        return photoRepository.findById(id);
    }

    public Photo createPhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }

    public Photo updatePhoto(Long id, Photo photoDetails) {
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new RuntimeException("Foto no encontrada"));
        try {
            photoDetails.setId(id);
            return photoRepository.save(photoDetails);
        } catch (RuntimeException e){
            System.out.println(e);
        }
        return null;
    }
}
