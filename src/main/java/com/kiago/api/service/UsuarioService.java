package com.kiago.api.service;

import com.kiago.api.dtos.CompanyDTO;
import com.kiago.api.dtos.UsuarioDTO;
import com.kiago.api.entities.Company;
import com.kiago.api.entities.Usuario;
import com.kiago.api.repositories.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository userRepository;
    private final ModelMapper modelMapper;

    public UsuarioService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public List<Usuario> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Usuario> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<Usuario> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<?> createUser(UsuarioDTO userDTO) {

        Usuario u = new Usuario(userDTO.getName(),userDTO.getEmail(),userDTO.getPassword());

        userRepository.save(u);
        return ResponseEntity.ok("Usuario creado con exito");
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /*public ResponseEntity<?> updateUser(Long id,UsuarioDTO userDetails) {
        Optional<Usuario> user = userRepository.findById(id);
        if (user.isPresent()) {
            userDetails.setId(id);
            return ResponseEntity.ok("Se a editado correctamente");
        }
        return null;
    }*/
    public ResponseEntity<?> updateUser(Long id, UsuarioDTO userDetails) {

        return userRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            userRepository.save(user);
            return ResponseEntity.ok("A editado el usuario correctamente.");
        }).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));

    }


    public void addCompany (Long userId, CompanyDTO companyDTO){

        Company company = modelMapper.map(companyDTO, Company.class);

        Usuario u = userRepository.findById(userId).orElseThrow();

        u.setCompany(company);

    }
}
