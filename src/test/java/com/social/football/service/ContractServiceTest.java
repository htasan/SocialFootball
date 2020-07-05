package com.social.football.service;

import com.social.football.exception.BadRequestException;
import com.social.football.exception.NotFoundException;
import com.social.football.model.Contract;
import com.social.football.model.Player;
import com.social.football.model.Team;
import com.social.football.model.dto.ContractRequestDto;
import com.social.football.repository.ContractRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContractServiceTest {

    @InjectMocks
    private ContractService service;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private PlayerService playerService;

    @Mock
    private TeamService teamService;

    @Test
    public void shouldCreate() {
        when(playerService.findById(1L)).thenReturn(new Player());
        when(teamService.findById(1L)).thenReturn(new Team());
        service.create(ContractRequestDto.builder().playerId(1L).teamId(1L).startYear(2018).endYear(2019).build());
        verify(contractRepository).save(any(Contract.class));
    }

    @Test
    public void shouldThrowExceptionWhenStartYearIsNotBeforeEndYear() {
        when(playerService.findById(1L)).thenReturn(new Player());
        when(teamService.findById(1L)).thenReturn(new Team());
        try {
            service.create(ContractRequestDto.builder().playerId(1L).teamId(1L).startYear(2017).endYear(2017).build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Contract end year should be after start year.", e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenStartYearIsNull() {
        when(playerService.findById(1L)).thenReturn(new Player());
        when(teamService.findById(1L)).thenReturn(new Team());
        try {
            service.create(ContractRequestDto.builder().playerId(1L).teamId(1L).endYear(2017).build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Start and end year should be specified.", e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenEndYearIsNull() {
        when(playerService.findById(1L)).thenReturn(new Player());
        when(teamService.findById(1L)).thenReturn(new Team());
        try {
            service.create(ContractRequestDto.builder().playerId(1L).teamId(1L).startYear(2017).build());
            fail();
        } catch (BadRequestException e) {
            assertEquals("Start and end year should be specified.", e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenPlayerNotFound() {
        doThrow(new NotFoundException("Player not found.")).when(playerService).findById(1L);
        try {
            service.create(ContractRequestDto.builder().playerId(1L).teamId(1L).startYear(2017).build());
            fail();
        } catch (NotFoundException e) {
            assertEquals("Player not found.", e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWhenTeamNotFound() {
        when(playerService.findById(1L)).thenReturn(new Player());
        doThrow(new NotFoundException("Team not found.")).when(teamService).findById(1L);
        try {
            service.create(ContractRequestDto.builder().playerId(1L).teamId(1L).startYear(2017).build());
            fail();
        } catch (NotFoundException e) {
            assertEquals("Team not found.", e.getMessage());
        }
    }
}
