package com.kiago.api.service;

import com.kiago.api.dtos.CompanyDTO;
import com.kiago.api.dtos.UsuarioDTO;
import com.kiago.api.entities.Company;
import com.kiago.api.entities.Usuario;
import com.kiago.api.repositories.IUsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository userRepository;
    private final ModelMapper modelMapper;

    public UsuarioService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public ResponseEntity<List<Usuario>> getAllUsers() {
        List<Usuario> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<Usuario> getUserById(Long id) {
        Usuario user =  userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario con id " + id + " no encontrado"));
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> getUserByEmail(String email) {
        if(!userRepository.existsByEmail(email)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El email " + email + " no existe");
        Usuario user = userRepository.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> createUser(UsuarioDTO userDTO) {

        if(userRepository.existsByEmail(userDTO.getEmail()) ) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario ya existe");
        Usuario u = modelMapper.map(userDTO, Usuario.class);

        userRepository.save(u);
        return ResponseEntity.ok("Usuario creado con exito");
    }

    public ResponseEntity<?> deleteUser(Long id) {
        if(!userRepository.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");

        userRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado correctamenete");

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
        if(!userRepository.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");

        Usuario usuario = userRepository.findById(id).orElseThrow();
        usuario.setId(id);
        usuario.setName(userDetails.getName());
        usuario.getUpdatedAt();
        usuario.setEmail(userDetails.getEmail());
        usuario.setPassword(userDetails.getPassword());

        return ResponseEntity.ok("Usuario editado correctamente");

    }


    public void addCompany (Long userId, CompanyDTO companyDTO){

        Company company = modelMapper.map(companyDTO, Company.class);
        Usuario u = userRepository.findById(userId).orElseThrow();
        u.setCompany(company);

    }
}
