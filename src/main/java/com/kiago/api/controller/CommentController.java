package com.kiago.api.controller;

import com.kiago.api.dtos.CommentDTO;
import com.kiago.api.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Optional<Comment> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @PutMapping("/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDetails) {
        commentService.updateComment(id, commentDetails);
    }
}
