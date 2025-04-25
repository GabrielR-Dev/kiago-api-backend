package com.kiago.api.repositories;

import com.kiago.api.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhotoRepository extends JpaRepository<Photo, Long> {
}
