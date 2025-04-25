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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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


    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public void updateComment(Long id, CommentDTO commentDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        Usuario user = usuarioRepository.getReferenceById(commentDetails.getUserId());
        Place place = placeRepository.getReferenceById(commentDetails.getPlaceId());

        try {

            /*Comment c = new Comment(
                    comment.getId(),
                    place,
                    user,
                    commentDetails.getComment(),
                    commentDetails.getRating()
            );*/
            System.out.println(comment.getId());



            //commentRepository.save(c);


        }catch (RuntimeException e){

        }



    }
}
