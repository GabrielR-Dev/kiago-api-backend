package com.kiago.api.service;

import com.kiago.api.dtos.PlaceDTO;
import com.kiago.api.entities.Place;
import com.kiago.api.entities.Usuario;
import com.kiago.api.repositories.IPlaceRepository;
import com.kiago.api.repositories.IUsuarioRepository;
import com.kiago.api.utils.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService implements Permission<Place> {

    @Autowired
    private IPlaceRepository placeRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public PlaceService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<PlaceDTO> getAllPlaces() {
        List<Place> places = placeRepository.findAll();
        List<PlaceDTO> placeDTOS = new ArrayList<>();

        places.forEach(place -> {
            placeDTOS.add(modelMapper.map(place, PlaceDTO.class));

        });
        return placeDTOS;
    }

    public PlaceDTO getPlaceById(Long id) {
        Place place = placeRepository.findById(id).orElseThrow();
        PlaceDTO placeDTO = modelMapper.map(place, PlaceDTO.class);
        return placeDTO;
    }

    public ResponseEntity<?> createPlace(PlaceDTO placeDTO) {

        Place place = modelMapper.map(placeDTO, Place.class);
        placeRepository.save(place);
        return ResponseEntity.ok("Creado con exito!");
    }

    public void deletePlace(Long placeId, Long userId) {

        Place place = placeRepository.getById(placeId);
        Usuario usuario = usuarioRepository.getById(userId);

        if (authorizeAction(place,usuario)) placeRepository.deleteById(placeId);


    }

    public Place updatePlace(Long id, Place placeDetails) {

        Optional<Place> place = placeRepository.findById(id);
        if (place.isPresent()) {
            placeDetails.setId(id);
            return placeRepository.save(placeDetails);
        }
        return null;
    }

    @Override
    public boolean authorizeAction(Place place, Usuario usuario) {

        boolean esCreador = place.getCreatedBy() != null && place.getCreatedBy().getId().equals(usuario.getId());
        boolean perteneceACompania = place.getCompany() != null
                && usuario.getCompany() != null
                && place.getCompany().getId().equals(usuario.getCompany().getId());

        return esCreador || perteneceACompania;
    }

}
