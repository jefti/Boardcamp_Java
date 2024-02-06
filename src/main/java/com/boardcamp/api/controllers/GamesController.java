package com.boardcamp.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.services.GamesService;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/games")
public class GamesController {
    final GamesService gamesService;

    GamesController(GamesService gamesService){
        this.gamesService = gamesService;
    }

    @PostMapping
    public ResponseEntity<GameModel> createGame(@RequestBody @Valid GameDTO body){
        if(gamesService.existsByName(body.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        GameModel game = gamesService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
}
