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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceService{


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
        // Creamos el usuario que esta conectado para guardarlo en el place
        String usuarioConnct = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(usuarioConnct).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no registrado"));

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
        // Validacion para verificar que sea el usuario conectado
        String usuarioContext = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioConnect = usuarioRepository.findByEmail(usuarioContext).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no registrado"));

        if(!placeRepository.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este id del lugar no esta registrado");
        if (usuarioConnect.getId() != id) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tienes acceso para modificar este usuario");


        Place place = placeRepository.getReferenceById(id);

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
        // Validacion para verificar que sea el usuario conectado
        String usuarioContext = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioConnect = usuarioRepository.findByEmail(usuarioContext).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no registrado"));

        Place place = placeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El lugar no existe"));

        if (place.getCreatedBy() == usuarioConnect){
            placeRepository.deleteById(id);
            return ResponseEntity.ok("Lugar borrado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tienes acceso para borrar este lugar");
        }
    }

}
