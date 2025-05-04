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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<?> getAllCommentsByPlace(Long placeId) {
        List<Comment> comments = commentRepository.findAllByPlaceId(placeId);
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


    public ResponseEntity<?> createComment(CommentDTO commentDTO, Long idPlace) {

        String usuarioContext = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioConnect = usuarioRepository.findByEmail(usuarioContext).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no registrado"));

        Place place = placeRepository.findById(idPlace).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No puedes agregar un comentario a este lugar"));

        if(usuarioConnect == place.getCreatedBy()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No puedes agregar un comentario en este lugar");

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPlace(place);
        comment.setUser(usuarioConnect);
        commentRepository.save(comment);
        return ResponseEntity.ok("Comentario agregado");
    }

    public ResponseEntity<?> deleteComment(Long id) {
        String usuarioContext = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioConnect = usuarioRepository.findByEmail(usuarioContext).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no registrado"));

        Comment comment = commentRepository.findById(id).orElseThrow(()->  new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El comentario no existe"));

        //List<Comment> commentsUser = commentRepository.findAllByUserId(usuarioConnect.getId());
        //boolean exists = commentsUser.stream()
        //        .anyMatch(c -> c.getId().equals(id));


        if (!comment.getUser().getId().equals(usuarioConnect.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes acceso para borrar este comentario");

        commentRepository.deleteById(id);
        return ResponseEntity.ok("Comentario eliminado");
    }

}
