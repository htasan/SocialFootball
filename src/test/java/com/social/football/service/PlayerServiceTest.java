package com.social.football.service;

import com.social.football.exception.BadRequestException;
import com.social.football.exception.NotFoundException;
import com.social.football.model.Contract;
import com.social.football.model.Player;
import com.social.football.model.Team;
import com.social.football.model.dto.PlayerRequestDto;
import com.social.football.repository.PlayerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerService service;

    @Mock
    private PlayerRepository playerRepository;

    @Test
    public void findAll() {
        service.findAll();
        verify(playerRepository).findAll();
    }

    @Test
    public void findById() {
        Optional<Player> player = Optional.of(new Player());
        when(playerRepository.findById(1L)).thenReturn(player);
        assertEquals(player.get(), service.findById(1L));
    }

    @Test
    public void throwExceptionIfNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            service.findById(1L);
            fail();
        } catch (NotFoundException e) {
            assertEquals("Player not found.", e.getMessage());
        }
    }

    @Test
    public void create() {
        service.create(PlayerRequestDto.builder().name("Messi").age(33).build());
        verify(playerRepository).save(any(Player.class));
    }

    @Test
    public void throwExceptionForEmptyName() {
        try {
            service.create(PlayerRequestDto.builder().name(" ").age(33).build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Name cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionForNullName() {
        try {
            service.create(PlayerRequestDto.builder().age(33).build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Name cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionForNullAge() {
        try {
            service.create(PlayerRequestDto.builder().name("Messi").build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Age cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionIfAgeIsZero() {
        try {
            service.create(PlayerRequestDto.builder().name("Messi").age(0).build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Age cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void calculateContractPrice() {
        Player player = Player.builder().name("Hakan").age(20).build();
        player.addContract(new Contract(player, new Team(), 2017, 2018));
        player.addContract(new Contract(player, new Team(), 2018, 2020));
        assertEquals((PlayerService.MONTHLY_FEE * 36 / 20) * 1.1, service.calculateContractPrice(player));
    }

    @Test
    public void update() {
        Player player = Player.builder().name("Messi").age(32).build();
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.save(player)).thenReturn(player);
        player = service.update(1L, PlayerRequestDto.builder().name("Messi").age(33).build());
        assertEquals(33, player.getAge());
    }

    @Test
    public void throwExceptionIfNotFoundWhenUpdate() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            service.update(1L, PlayerRequestDto.builder().build());
            fail();
        } catch (NotFoundException e) {
            assertEquals("Player not found.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionIfNameNotFoundWhenUpdate() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(new Player()));
        try {
            service.update(1L, PlayerRequestDto.builder().age(33).build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Name cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionIfAgeNotFoundWhenUpdate() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(new Player()));
        try {
            service.update(1L, PlayerRequestDto.builder().name("Messi").build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Age cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void delete() {
        service.delete(1L);
        verify(playerRepository).deleteById(1L);
    }

    @Test
    public void throwExceptionIfNotFoundWhenDelete() {
        doThrow(new EmptyResultDataAccessException(0)).when(playerRepository).deleteById(1L);
        try {
            service.delete(1L);
            fail();
        } catch (NotFoundException e) {
            assertEquals("Player not found.", e.getMessage());
        }
    }
}
