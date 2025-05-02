package com.kiago.api.controller;

import com.kiago.api.dtos.CommentDTO;
import com.kiago.api.entities.Comment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kiago.api.service.CommentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        return commentService.createComment(commentDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}
