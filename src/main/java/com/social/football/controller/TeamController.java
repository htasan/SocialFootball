package com.social.football.controller;

import com.social.football.model.dto.*;
import com.social.football.service.converter.ContractDtoListConverter;
import com.social.football.service.TeamService;
import com.social.football.service.converter.TeamResponseDtoConverter;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @ApiOperation(value = "List all teams", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved team list")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TeamResponseDto> teams() {
        return teamService.findAll().stream().map(TeamResponseDtoConverter::convert).collect(Collectors.toList());
    }

    @ApiOperation(value = "List players of the team", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved players of team"),
            @ApiResponse(code = 404, message = "Team could not be found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public List<TeamPlayersDto> findPlayersById(@PathVariable("id") Long id) {
        return ContractDtoListConverter.convertToTeamPlayersList(teamService.findById(id).getContractList());
    }

    @ApiOperation(value = "List players of the team for selected year", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved players of team for selected year"),
            @ApiResponse(code = 404, message = "Team could not be found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/{year}")
    public List<TeamPlayersDto> findPlayersByIdAndYear(@PathVariable("id") Long id, @PathVariable("year") Integer year) {
        return ContractDtoListConverter.convertToTeamPlayersList(teamService.findContractsByIdAndYear(id, year));
    }

    @ApiOperation(value = "Create team", response = TeamResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Team successfully created"),
            @ApiResponse(code = 400, message = "Team could not be created")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TeamResponseDto create(@RequestBody TeamRequestDto teamRequestDto) {
        return TeamResponseDtoConverter.convert(teamService.create(teamRequestDto));
    }

    @ApiOperation(value = "Update team", response = TeamResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team successfully updated"),
            @ApiResponse(code = 400, message = "Team could not be updated"),
            @ApiResponse(code = 404, message = "Team could not be found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TeamResponseDto update(@PathVariable("id") Long id, @RequestBody TeamRequestDto teamRequestDto) {
        return TeamResponseDtoConverter.convert(teamService.update(id, teamRequestDto));
    }

    @ApiOperation(value = "Delete team")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team successfully deleted"),
            @ApiResponse(code = 404, message = "Team could not be found")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        teamService.delete(id);
    }

    @Autowired
    private TeamService teamService;
}
