package com.kiago.api.repositories;

import com.kiago.api.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository  extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}