package com.kiago.api.controller;

import com.kiago.api.config.JwtUtil;
import com.kiago.api.dtos.AuthRequest;
import com.kiago.api.dtos.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users/auth")
@Tag(name = "Users", description = "Operaciones relacionadas al inicio de sesion")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    @Operation(summary = "Endpoint iniciar sesion",description = "Este endpoin iniciar sesion. " +
            "No necesita token")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        System.out.println("------------------------------------------------------------- "+request.getEmail() +" "+request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

        String jwt = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
