package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class GameIntegrationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired    
    private GamesRepository gamesRepository;
    @Autowired    
    private RentalsRepository rentalsRepository;

    @BeforeEach
    void CleanUpDatabaseBefore(){
        rentalsRepository.deleteAll();
        gamesRepository.deleteAll();
    }


    @AfterAll
    void CleanUpDatabaseAfter(){
        rentalsRepository.deleteAll();
        gamesRepository.deleteAll();
    }

    @Test
    void GivenRepeatGame_WhenCreating_ThrowsException(){

        GameDTO gameDTO = new GameDTO("game", "image", 1,1000);
        GameModel game = new GameModel(gameDTO);
        HttpEntity body = new HttpEntity<>(gameDTO);
        gamesRepository.save(game);

        ResponseEntity<String> response = restTemplate.exchange(
            "/games",
            HttpMethod.POST,
            body,
            String.class
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void GivenValidGame_WhenCreating_CreateGame(){

        GameDTO gameDTO = new GameDTO("game", "image", 1,1000);
        GameModel game = new GameModel(gameDTO);
        HttpEntity body = new HttpEntity<>(gameDTO);


        ResponseEntity<String> response = restTemplate.exchange(
            "/games",
            HttpMethod.POST,
            body,
            String.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, gamesRepository.count()); 
    }


}
