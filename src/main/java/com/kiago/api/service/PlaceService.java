package com.kiago.api.service;

import com.kiago.api.dtos.PlaceDTO;
import com.kiago.api.entities.Company;
import com.kiago.api.entities.Place;
import com.kiago.api.entities.Usuario;
import com.kiago.api.repositories.ICompanyRepository;
import com.kiago.api.repositories.IPlaceRepository;
import com.kiago.api.repositories.IUsuarioRepository;
import com.kiago.api.utils.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceService implements Permission<Place> {


    @Autowired
    private IPlaceRepository placeRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ICompanyRepository companyRepository;

    private final ModelMapper modelMapper;

    public PlaceService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<?> getAllPlaces() {
        Usuario us = usuarioRepository.getReferenceById(10l);
        List<Place> places = placeRepository.findAll();
        List<PlaceDTO> placeDTOs = places.stream()
                .map(place -> {
                    PlaceDTO dto = modelMapper.map(place, PlaceDTO.class);
                    if(dto.getCompany() != null) dto.setCompany(place.getCompany().getName());
                    dto.setCreatedBy(String.valueOf(us.getName()));
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(placeDTOs);
    }

    public ResponseEntity<?> getPlaceById(Long id) {

        if(!placeRepository.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id no existe");

        Place place = placeRepository.getReferenceById(id);
        PlaceDTO placeDTO = modelMapper.map(place, PlaceDTO.class);
        //placeDTO.setCompany(place.getCompany(),);
        return ResponseEntity.ok(placeDTO);

    }

    public ResponseEntity<?> createPlace(PlaceDTO placeDTO) {
        // Buscar usuario (por ahora fijo en 15L) si no tiene un usuario da error
        Usuario usuario = usuarioRepository.getReferenceById(15l);

        // Mapear el DTO a entidad
        Place place = modelMapper.map(placeDTO, Place.class);
        place.setCreatedBy(usuario);

        // Si viene una companyName, buscar la Company y setearla
        if (placeDTO.getCompany() != null && !placeDTO.getCompany().isEmpty()) {
            Optional<Company> companyOptional = companyRepository.findByNameIgnoreCase(placeDTO.getCompany());
            if (companyOptional.isPresent()) {
                place.setCompany(companyOptional.get());
            } else {
                return ResponseEntity.badRequest().body("La empresa " + placeDTO.getCompany() + " no existe.");
            }
        }

        // Guardar el Place en la base de datos
        placeRepository.save(place);

        return ResponseEntity.status(HttpStatus.CREATED).body("Lugar creado exitosamente");
    }

    public ResponseEntity<?> updatePlace(Long id, PlaceDTO placeDTO) {

        if(!placeRepository.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este id del lugar no esta registrado");

        Place place = placeRepository.getReferenceById(id);

        //se debe comparar si el usuario conectado le pertenece o fue el que creo este Place
        //if(place.getCreatedBy().getId() != id) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No puedes editar este Lugar");

        if(placeDTO.getName() != null) place.setName(placeDTO.getName());
        if(placeDTO.getAddress() != null)place.setAddress(placeDTO.getAddress());
        if(placeDTO.getLatitude() != null)place.setLatitude(placeDTO.getLatitude());
        if(placeDTO.getLongitude() != null)place.setLongitude(place.getLongitude());
        if(placeDTO.getCategory() != null)place.setCategory(placeDTO.getCategory());
        if(placeDTO.getDescription() != null)place.setDescription(placeDTO.getDescription());
        if(placeDTO.getCompany() != null)place.setDescription(placeDTO.getCompany());



        placeRepository.save(place);

        return ResponseEntity.ok("Se a editado correctamente");
    }

    public ResponseEntity<?> deletePlace(Long id) {
        if (placeRepository.existsById(id)) {
            placeRepository.deleteById(id);
            return ResponseEntity.ok("Lugar borrado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EL lugar con el id " + id + "no existe");
        }
    }

    @Override
    public boolean authorizeAction(Place recurso, Usuario usuario) {
        return false;
    }
}
