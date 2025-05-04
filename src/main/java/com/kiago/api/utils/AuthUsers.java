package com.kiago.api.utils;

import com.kiago.api.entities.Usuario;
import com.kiago.api.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUsers {

    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    ICommentRepository commentRepository;
    @Autowired
    ICompanyRepository companyRepository;
    @Autowired
    IPlaceRepository placeRepository;
    @Autowired
    IPhotoRepository photoRepository;

    @Autowired
    ModelMapper modelMapper;

    public boolean UsuarioConect(Long idEntity){
        Object usuarioConect = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = modelMapper.map(usuarioConect, Usuario.class);

        return false;
    }
}
