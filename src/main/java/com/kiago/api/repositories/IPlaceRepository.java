package com.kiago.api.repositories;

import com.kiago.api.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlaceRepository extends JpaRepository<Place, Long> {
}