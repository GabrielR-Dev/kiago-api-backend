package com.kiago.api.controller;

import com.kiago.api.dtos.UsuarioDTO;
import com.kiago.api.entities.Usuario;
import com.kiago.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;

    @GetMapping
    public List<Usuario> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public Optional<Usuario> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UsuarioDTO user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UsuarioDTO userDetails) {
        return userService.updateUser(id, userDetails);
    }
}
