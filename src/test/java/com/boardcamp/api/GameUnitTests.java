package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.exceptions.GameConflictException;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.services.GamesService;

@SpringBootTest
public class GameUnitTests {
    @InjectMocks
    private GamesService gamesService;

    @Mock
    private GamesRepository gamesRepository;

    @Test
    void givenRepeatedName_whenCreating_thenThrowconflict() {
        //given
        GameDTO body = new GameDTO("name","imageLink",3,1000);
        when(gamesRepository.existsByName("name")).thenReturn(true);
        
        //when
        GameConflictException exception = assertThrows(
            GameConflictException.class, 
            ()-> gamesService.save(body));
        
        //then
        assertNotNull(exception);
        assertEquals(
            "Um jogo com o nome '" + body.getName()+"' já foi registrado.", 
            exception.getMessage());
        verify(gamesRepository, times(0)).save(any());
        
    }

    @Test
    void givenValidBody_whenCreating_thenCreateGame() {
        //given
        GameDTO body = new GameDTO("name","imageLink",3,1000);
        GameModel createdGame = new GameModel(body);
        when(gamesRepository.existsByName("name")).thenReturn(false);
        when(gamesRepository.save(any())).thenReturn(createdGame);
        
        //when
       GameModel response = gamesService.save(body);
        
        //then
        assertNotNull(response);
        verify(gamesRepository, times(1)).existsByName(any());
        verify(gamesRepository, times(1)).save(any());
        assertEquals(createdGame, response);
        
    }
}
