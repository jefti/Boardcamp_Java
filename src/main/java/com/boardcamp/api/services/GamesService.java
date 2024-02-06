package com.boardcamp.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GamesRepository;

@Service
public class GamesService {
    
    final GamesRepository gamesRepository;

    GamesService(GamesRepository gamesRepository){
        this.gamesRepository = gamesRepository;
    }

    public GameModel save(GameDTO dto){
        GameModel gameModel = new GameModel(dto);
        return gamesRepository.save(gameModel);
    }

    public boolean existsByName(String name){
        return gamesRepository.existsByName(name);
    }

    public List<GameModel> findall(){
        return gamesRepository.findAll();
    }
}
