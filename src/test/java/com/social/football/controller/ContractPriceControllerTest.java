package com.social.football.controller;

import com.social.football.model.Contract;
import com.social.football.model.Player;
import com.social.football.service.PlayerService;
import com.social.football.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class ContractPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @MockBean
    private PlayerService playerService;

    @Test
    public void findContractPriceByPlayerIdForTeam() throws Exception {
        when(teamService.formatPlayerPriceForTeam(playerService.findById(1L), teamService.findById(1L))).thenReturn("TRY7,000,000.00");
        mockMvc.perform(get("/contractprice/{playerId}/{teamId}", "1", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("TRY7,000,000.00"));
    }
}
