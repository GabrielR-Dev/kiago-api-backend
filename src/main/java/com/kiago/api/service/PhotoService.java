package com.kiago.api.service;

import com.kiago.api.dtos.PhotoDTO;
import com.kiago.api.entities.Photo;
import com.kiago.api.entities.Place;
import com.kiago.api.entities.Usuario;
import com.kiago.api.repositories.IPhotoRepository;
import com.kiago.api.repositories.IPlaceRepository;
import com.kiago.api.repositories.IUsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoService {
    @Autowired
    private IPhotoRepository photoRepository;

    @Autowired
    private IPlaceRepository placeRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper;

    public PhotoService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<?> getAllPhotos() {
        List<Photo> photos = photoRepository.findAll();
        List<PhotoDTO> photoDTOs = photos.stream()
                .map(photo -> {
                    PhotoDTO dto = modelMapper.map(photo, PhotoDTO.class);
                    dto.setPlaceId(photo.getPlace().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(photoDTOs);
    }



    public ResponseEntity<?> getPhotoById(Long id) {
        Optional<Photo> photoOptional = photoRepository.findById(id);
        if (!photoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Foto con el id " + id + " no encontrada");
        }

        PhotoDTO dto = modelMapper.map(photoOptional.get(), PhotoDTO.class);
        dto.setPlaceId(photoOptional.get().getPlace().getId());

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> getPhotoByIdPlace(Long idPlace) {

        List<Photo> photosPlace = photoRepository.findAllPhotosByPlaceId(idPlace);

        List<PhotoDTO> photoDTOS = photosPlace.stream()
                .map(p -> modelMapper.map(p, PhotoDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(photoDTOS);

    }

    public ResponseEntity<?> addPhoto(PhotoDTO photoDTO, Long idPlace) {

        String usuarioContext = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioConnect = usuarioRepository.findByEmail(usuarioContext).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no registrado"));

        Place place = placeRepository.findById(idPlace).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El lugar no existe"));
        if(usuarioConnect != place.getCreatedBy()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No puedes agregar una foto a este Lugar");

        Photo photo = modelMapper.map(photoDTO, Photo.class);
        photo.setPlace(place);
        Photo savedPhoto = photoRepository.save(photo);

        PhotoDTO savedPhotoDTO = modelMapper.map(savedPhoto, PhotoDTO.class);
        savedPhotoDTO.setPlaceId(savedPhoto.getPlace().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPhotoDTO);
    }


    public ResponseEntity<?> deletePhoto(Long id) {

        Photo photo = photoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "La foto no existe"));


        Optional<Photo> photoOptional = photoRepository.findById(id);
        if (!photoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Foto con el id " + id + " no encontrado");
        }

        photoRepository.deleteById(id);
        return ResponseEntity.ok("Foto borrada correctamente");
    }

}
