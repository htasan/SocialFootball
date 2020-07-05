package com.social.football.controller;

import com.social.football.model.Contract;
import com.social.football.model.Player;
import com.social.football.model.Team;
import com.social.football.model.dto.PlayerRequestDto;
import com.social.football.service.PlayerService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Test
    public void players() throws Exception {
        when(playerService.findAll()).thenReturn(Arrays.asList(
                Player.builder().id(1L).name("Messi").age(33).build(),
                Player.builder().id(2L).name("Ronaldo").age(34).build()));
        mockMvc.perform(get("/players")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Messi"))
                .andExpect(jsonPath("$[0].age").value(33))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Ronaldo"))
                .andExpect(jsonPath("$[1].age").value(34));
    }

    @Test
    public void findTeamsById() throws Exception {
        Player player = Player.builder().id(1L).name("Messi").age(33).build();
        player.addContract(new Contract(player, Team.builder().name("Galatasaray").build(), 2019, 2020));
        player.addContract(new Contract(player, Team.builder().name("Real Madrid").build(), 2015, 2019));
        when(playerService.findById(1L)).thenReturn(player);
        mockMvc.perform(get("/players/{id}", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].yearInfo").value("2019"))
                .andExpect(jsonPath("$[0].teamName").value("Galatasaray"))
                .andExpect(jsonPath("$[1].yearInfo").value("2015-2018"))
                .andExpect(jsonPath("$[1].teamName").value("Real Madrid"));
    }

    @Test
    public void create() throws Exception {
        when(playerService.create(PlayerRequestDto.builder().name("Messi").age(33).build()))
                .thenReturn(Player.builder().id(1L).name("Messi").age(33).build());
        mockMvc.perform(post("/players")
                .contentType("application/json").content("{\n" +
                        "     \"name\": \"Messi\",\n" +
                        " \t \"age\" :33\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Messi"))
                .andExpect(jsonPath("$.age").value(33));
    }

    @Test
    public void update() throws Exception {
        when(playerService.update(1L, PlayerRequestDto.builder().name("Messi").age(33).build()))
                .thenReturn(Player.builder().id(1L).name("Messi").age(33).build());
        mockMvc.perform(put("/players/{id}", "1")
                .contentType("application/json").content("{\n" +
                        "     \"name\": \"Messi\",\n" +
                        " \t \"age\" :33\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Messi"))
                .andExpect(jsonPath("$.age").value(33));
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/players/{id}", "1")
                .contentType("application/json"))
                .andExpect(status().isOk());
        verify(playerService).delete(1L);
    }
}
