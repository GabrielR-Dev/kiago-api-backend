package com.kiago.api.repositories;

import com.kiago.api.entities.Comment;
import com.kiago.api.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT p FROM Photo p WHERE p.place.id = :placeId")
    List<Photo> findAllPhotosByPlaceId(@Param("placeId") Long placeId);
}
