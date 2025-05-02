package com.kiago.api.service;

import com.kiago.api.dtos.CommentDTO;
import com.kiago.api.entities.Comment;
import com.kiago.api.entities.Place;
import com.kiago.api.entities.Usuario;
import com.kiago.api.repositories.ICommentRepository;
import com.kiago.api.repositories.IPlaceRepository;
import com.kiago.api.repositories.IUsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IPlaceRepository placeRepository;
    private final ModelMapper modelMapper;

    //Constructor
    public CommentService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public ResponseEntity<?> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDTO> commentsDTO = comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(commentsDTO);
    }

    public ResponseEntity<?> getCommentById(Long id) {


        if(!usuarioRepository.existsById(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario con el id " + id + " no existe");

        Comment comentario = commentRepository.findById(id).orElseThrow();
        CommentDTO commentDTO = modelMapper.map(comentario, CommentDTO.class);
        return ResponseEntity.ok(commentDTO);
    }


    public ResponseEntity<?> createComment(CommentDTO commentDTO) {

        if (commentDTO.getPlaceId() == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay un lugar registrado");
        if(commentDTO.getUserId() == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El usuario no esta registrado");

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        commentRepository.save(comment);
        return ResponseEntity.ok("Comentario agregado");
    }

    public ResponseEntity<?> deleteComment(Long id) {


        List<Comment> commentsUser = commentRepository.findAllByUserId(11l);
        boolean exists = commentsUser.stream()
                .anyMatch(c -> c.getId().equals(id));


        if(!exists) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El comentario con el id "+id+" no existe");
        Comment comment = commentRepository.getReferenceById(id);
        //Ver si al id del usuario conectado le pertenece el comentario para poder borrarlo
        //if (comment.getUser().getId() == id)

        commentRepository.deleteById(id);
        return ResponseEntity.ok("Comentario eliminado");
    }

}
