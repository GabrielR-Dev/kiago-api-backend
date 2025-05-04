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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public UsuarioService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public ResponseEntity<List<UsuarioDTO>> getAllUsers() {
        List<Usuario> users = userRepository.findAll();
        List<UsuarioDTO> usuarioDTOS = users.stream().map(
                user -> modelMapper.map(user,UsuarioDTO.class)
        ).collect(Collectors.toList());
        return ResponseEntity.ok(usuarioDTOS);
    }

    public ResponseEntity<UsuarioDTO> getUserById(Long id) {
        System.out.println("Este es el usuario que inicio sesion: -----------------> "+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Usuario user =  userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario con id " + id + " no encontrado"));
        UsuarioDTO usuarioDTO = modelMapper.map(user,UsuarioDTO.class);
        return ResponseEntity.ok(usuarioDTO);
    }

    public ResponseEntity<?> getUserByEmail(String email) {
        if(!userRepository.existsByEmail(email)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El email " + email + " no existe");
        Usuario user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        UsuarioDTO userDTO= modelMapper.map(user,UsuarioDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    public ResponseEntity<?> createUser(UsuarioDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail()) ) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario ya existe");
        Usuario u = modelMapper.map(userDTO,Usuario.class);
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        userRepository.save(u);
        return ResponseEntity.ok("Usuario creado con exito");
    }

    public ResponseEntity<?> deleteUser(Long id) {
        // Creamos el usuario que esta conectado para guardarlo en el place
        String usuarioContext = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioConnect = userRepository.findByEmail(usuarioContext).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no registrado"));
        if(id != usuarioConnect.getId()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No puedes borrar este usuario");

        userRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado correctamenete");

    }

    public ResponseEntity<?> updateUser(Long id, UsuarioDTO usuarioDTO) {
        if(!userRepository.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");

        String usuarioContext = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioConnect = userRepository.findByEmail(usuarioContext).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no registrado"));
        if (usuarioConnect.getId() != id) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No puedes modificar este usuario");


        Usuario usuario = userRepository.findById(id).orElseThrow();
        usuario.setId(id);
        if(usuarioDTO.getName() != null)usuario.setName(usuarioDTO.getName());
        if(usuarioDTO.getEmail() != null)usuario.setEmail(usuarioDTO.getEmail());
        if(usuarioDTO.getPassword() != null) usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.getUpdatedAt();

        userRepository.save(usuario);


        return ResponseEntity.ok("Usuario editado correctamente");

    }


    public void addCompany (Long userId, CompanyDTO companyDTO){

        Company company = modelMapper.map(companyDTO, Company.class);
        Usuario u = userRepository.findById(userId).orElseThrow();
        u.setCompany(company);

    }
}
