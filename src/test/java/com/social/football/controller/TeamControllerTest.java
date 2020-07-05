package com.social.football.controller;

import com.social.football.model.Contract;
import com.social.football.model.Player;
import com.social.football.model.Team;
import com.social.football.model.dto.TeamRequestDto;
import com.social.football.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Test
    public void teams() throws Exception {
        when(teamService.findAll()).thenReturn(Arrays.asList(
                Team.builder().id(1L).name("Galatasaray").currencyCode("TRY").build(),
                Team.builder().id(2L).name("Barcelona").currencyCode("EUR").build()));
        mockMvc.perform(get("/teams")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Galatasaray"))
                .andExpect(jsonPath("$[0].currencyCode").value("TRY"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Barcelona"))
                .andExpect(jsonPath("$[1].currencyCode").value("EUR"));
    }

    @Test
    public void findPlayersById() throws Exception {
        Team team = Team.builder().name("Galatasaray").build();
        team.addContract(new Contract(Player.builder().id(1L).name("Messi").age(33).build(), team, 2019, 2020));
        team.addContract(new Contract(Player.builder().id(2L).name("Ronaldo").age(34).build(), team, 2015, 2019));
        when(teamService.findById(1L)).thenReturn(team);
        mockMvc.perform(get("/teams/{id}", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].playerId").value(1L))
                .andExpect(jsonPath("$[0].playerName").value("Messi"))
                .andExpect(jsonPath("$[1].playerId").value(2L))
                .andExpect(jsonPath("$[1].playerName").value("Ronaldo"));
    }

    @Test
    public void findPlayersByIdAndYear() throws Exception {
        List<Contract> contractList = Collections.singletonList(Contract.builder().player(
                Player.builder().id(2L).name("Ronaldo").build()).build());
        when(teamService.findContractsByIdAndYear(1L, 2018)).thenReturn(contractList);
        mockMvc.perform(get("/teams/{id}/{year}", "1", "2018")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].playerId").value(2L))
                .andExpect(jsonPath("$[0].playerName").value("Ronaldo"));
    }

    @Test
    public void create() throws Exception {
        when(teamService.create(TeamRequestDto.builder().name("Galatasaray").currencyCode("TRY").build()))
                .thenReturn(Team.builder().id(1L).name("Galatasaray").currencyCode("TRY").build());
        mockMvc.perform(post("/teams")
                .contentType("application/json").content("{\n" +
                        "     \"name\": \"Galatasaray\",\n" +
                        " \t \"currencyCode\": \"TRY\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Galatasaray"))
                .andExpect(jsonPath("$.currencyCode").value("TRY"));
    }

    @Test
    public void update() throws Exception {
        when(teamService.update(1L, TeamRequestDto.builder().name("Galatasaray").currencyCode("TRY").build()))
                .thenReturn(Team.builder().id(1L).name("Galatasaray").currencyCode("TRY").build());
        mockMvc.perform(put("/teams/{id}", "1")
                .contentType("application/json").content("{\n" +
                        "     \"name\": \"Galatasaray\",\n" +
                        " \t \"currencyCode\": \"TRY\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Galatasaray"))
                        .andExpect(jsonPath("$.currencyCode").value("TRY"));
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/teams/{id}", "1")
                .contentType("application/json"))
                .andExpect(status().isOk());
        verify(teamService).delete(1L);
    }
}
