package com.social.football.controller;

import com.social.football.model.Player;
import com.social.football.model.Team;
import com.social.football.service.PlayerService;
import com.social.football.service.TeamService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contractprice")
public class ContractPriceController {

    @ApiOperation(value = "Find contract price of the player for team's currency", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated contract price"),
            @ApiResponse(code = 404, message = "Player or team could not be found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{playerId}/{teamId}")
    public String findContractPriceByPlayerIdForTeam(@PathVariable("playerId") Long playerId, @PathVariable("teamId") Long teamId) {
        return teamService.formatPlayerPriceForTeam(playerService.findById(playerId), teamService.findById(teamId));
    }

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;
}
