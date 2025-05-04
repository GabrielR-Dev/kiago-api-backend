package com.kiago.api.repositories;

import com.kiago.api.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlaceRepository extends JpaRepository<Place, Long> {
    @Override
    boolean existsById(Long aLong);

    List<Place> findAllByCreatedById(Long userId);}