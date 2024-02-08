package com.boardcamp.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.boardcamp.api.models.RentalModel;

@Repository
public interface RentalsRepository extends JpaRepository<RentalModel, Long>{
    
    Optional<RentalModel> findById(long id);
    
    @Query(value = "SELECT * FROM rentals WHERE game_id = :gameId and return_date IS NULL", nativeQuery = true)
    List<RentalModel> findValidRentalById(@Param("gameId") Long gameId);
}
