package com.social.football.controller;

import com.social.football.model.Contract;
import com.social.football.model.Player;
import com.social.football.model.Team;
import com.social.football.model.dto.ContractRequestDto;
import com.social.football.model.dto.TeamRequestDto;
import com.social.football.service.ContractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    @Test
    public void create() throws Exception {
        when(contractService.create(ContractRequestDto.builder().playerId(1L).teamId(1L).startYear(2019).endYear(2020).build()))
                .thenReturn(Contract.builder().id(1L).player(Player.builder().name("Messi").build())
                        .team(Team.builder().name("Galatasaray").build())
                        .startDate(LocalDateTime.of(2019, 1, 1, 0, 0, 0))
                        .endDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0)).build());
        mockMvc.perform(post("/contracts")
                .contentType("application/json").content("{\n" +
                        "     \"playerId\": 1,\n" +
                        " \t \"teamId\": 1,\n" +
                        " \t \"startYear\": 2019,\n" +
                        " \t \"endYear\": 2020\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.playerName").value("Messi"))
                .andExpect(jsonPath("$.startDate").value("2019-01-01T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-01-01T00:00:00"));
    }
}
