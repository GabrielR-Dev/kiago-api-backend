package com.kiago.api.controller;

import com.kiago.api.dtos.CommentDTO;
import com.kiago.api.entities.Comment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kiago.api.service.CommentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Users", description = "Peticiones para los Comentarios de los Lugares")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping

    @Operation(summary = "Endpoint trae todos los comentarios")
    public ResponseEntity<?> getAllComments() {
        return commentService.getAllComments();
    }
    @Operation(summary = "Endpoint trae todos los comentarios del Lugar con el ID que se le pasa en la URL")
    @GetMapping("/place/{idPlace}")
    public ResponseEntity<?> getAllCommentsByPlace(@PathVariable Long idPlace) {
        return commentService.getAllCommentsByPlace(idPlace);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint para traer el comentario por ID",description = "Este endpoin necesita el id del Comentario. " +
            "Necesita token")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping("/{placeId}")
    @Operation(summary = "Endpoint para crear un Comentario al Lugar",description = "Este endpoin se le debe pasar el ID del Lugar por la URL. " +
            "Necesita token")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDTO commentDTO, @PathVariable Long placeId) {
        return commentService.createComment(commentDTO,placeId);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint para borrar el Comentario",description = "Este endpoin el comentario con el ID que se le paso por URL. " +
            "Necesita token")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}
