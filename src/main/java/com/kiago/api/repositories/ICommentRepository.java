package com.kiago.api.repositories;

import com.kiago.api.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId")
    List<Comment> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Comment c WHERE c.place.id = :placeId")
    List<Comment> findAllByPlaceId(@Param("placeId") Long placeId);
}
