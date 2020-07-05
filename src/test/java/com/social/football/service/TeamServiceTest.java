package com.social.football.service;

import com.social.football.exception.BadRequestException;
import com.social.football.exception.NotFoundException;
import com.social.football.model.Contract;
import com.social.football.model.Player;
import com.social.football.model.Team;
import com.social.football.model.dto.TeamRequestDto;
import com.social.football.repository.TeamRepository;
import com.social.football.validator.TeamInfoValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceTest {

    @InjectMocks
    private TeamService service;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerService playerService;

    @Test
    public void findAll() {
        service.findAll();
        verify(teamRepository).findAll();
    }

    @Test
    public void findById() {
        Optional<Team> team = Optional.of(new Team());
        when(teamRepository.findById(1L)).thenReturn(team);
        assertEquals(team.get(), service.findById(1L));
    }

    @Test
    public void findContractsByIdAndYear() {
        Team team = Team.builder().name("Galatasaray").currencyCode("TRY").build();
        team.addContract(new Contract(new Player(), team, 2017, 2020));
        team.addContract(new Contract(new Player(), team, 2018, 2019));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        assertEquals(1, service.findContractsByIdAndYear(1L, 2017).size());
        assertEquals(2, service.findContractsByIdAndYear(1L, 2018).size());
        assertEquals(1, service.findContractsByIdAndYear(1L, 2019).size());
    }

    @Test
    public void formatPlayerPriceForTheTeam() {
        Team team = Team.builder().name("Galatasaray").currencyCode("TRY").build();
        Player player = new Player();
        when(playerService.calculateContractPrice(player)).thenReturn(750000d);
        assertEquals("TRY750,000.00", service.formatPlayerPriceForTeam(player, team));
    }

    @Test
    public void throwExceptionIfNotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            service.findById(1L);
            fail();
        } catch (NotFoundException e) {
            assertEquals("Team not found.", e.getMessage());
        }
    }

    @Test
    public void create() {
        service.create(TeamRequestDto.builder().name("Galatasaray").currencyCode("TRY").build());
        verify(teamRepository).save(any(Team.class));
    }

    @Test
    public void throwExceptionForEmptyName() {
        try {
            service.create(TeamRequestDto.builder().name(" ").currencyCode("TRY").build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Name cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionForNullName() {
        try {
            service.create(TeamRequestDto.builder().currencyCode("TRY").build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Name cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionIfCurrencyIsInvalid() {
        try {
            service.create(TeamRequestDto.builder().name("Galatasaray").currencyCode("TRYEUR").build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Currency code is not valid. " + TeamInfoValidator.CURRENCY_CODE_LIST, e.getMessage());
        }
    }

    @Test
    public void throwExceptionIfCurrencyCodeIsNull() {
        try {
            service.create(TeamRequestDto.builder().name("Galatasaray").build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Currency code is required. " + TeamInfoValidator.CURRENCY_CODE_LIST, e.getMessage());
        }
    }

    @Test
    public void update() {
        Team team = Team.builder().name("Başakşehir").currencyCode("TRY").build();
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamRepository.save(team)).thenReturn(team);
        team = service.update(1L, TeamRequestDto.builder().name("Medipol Başakşehir").currencyCode("TRY").build());
        assertEquals("Medipol Başakşehir", team.getName());
    }

    @Test
    public void throwExceptionIfNotFoundWhenUpdate() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            service.update(1L, TeamRequestDto.builder().build());
            fail();
        } catch (NotFoundException e) {
            assertEquals("Team not found.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionIfNameNotFoundWhenUpdate() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(new Team()));
        try {
            service.update(1L, TeamRequestDto.builder().currencyCode("TRY").build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Name cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionIfCurrencyCodeNotFoundWhenUpdate() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(new Team()));
        try {
            service.update(1L, TeamRequestDto.builder().name("Galarasaray").build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Currency code is required. " + TeamInfoValidator.CURRENCY_CODE_LIST, e.getMessage());
        }
    }

    @Test
    public void throwExceptionIfCurrencyCodeIsInvalidWhenUpdate() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(new Team()));
        try {
            service.update(1L, TeamRequestDto.builder().name("Galatasaray").currencyCode("").build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Currency code is not valid. " + TeamInfoValidator.CURRENCY_CODE_LIST, e.getMessage());
        }
    }

    @Test
    public void delete() {
        service.delete(1L);
        verify(teamRepository).deleteById(1L);
    }

    @Test
    public void throwExceptionIfNotFoundWhenDelete() {
        doThrow(new EmptyResultDataAccessException(0)).when(teamRepository).deleteById(1L);
        try {
            service.delete(1L);
            fail();
        } catch (NotFoundException e) {
            assertEquals("Team not found.", e.getMessage());
        }
    }
}
