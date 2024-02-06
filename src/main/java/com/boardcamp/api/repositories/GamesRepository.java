package com.boardcamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boardcamp.api.models.GameModel;

@Repository
public interface GamesRepository extends JpaRepository<GameModel, Long>{
    boolean existsByName(String name);
}
