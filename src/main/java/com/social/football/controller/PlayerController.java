package com.social.football.controller;

import com.social.football.model.dto.PlayerTeamsDto;
import com.social.football.model.dto.PlayerRequestDto;
import com.social.football.model.dto.PlayerResponseDto;
import com.social.football.service.converter.ContractDtoListConverter;
import com.social.football.service.PlayerService;
import com.social.football.service.converter.PlayerResponseDtoConverter;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @ApiOperation(value = "List all players", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved player list")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PlayerResponseDto> players() {
        return playerService.findAll().stream().map(PlayerResponseDtoConverter::convert).collect(Collectors.toList());
    }

    @ApiOperation(value = "List teams of the player", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved teams of player"),
            @ApiResponse(code = 404, message = "Player could not be found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public List<PlayerTeamsDto> findTeamsById(@PathVariable("id") Long id) {
        return ContractDtoListConverter.convertToPlayerTeamsList(playerService.findById(id).getContractList());
    }

    @ApiOperation(value = "Create player", response = PlayerResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Player successfully created"),
            @ApiResponse(code = 400, message = "Player could not be created")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PlayerResponseDto create(@RequestBody PlayerRequestDto playerRequestDto) {
        return PlayerResponseDtoConverter.convert(playerService.create(playerRequestDto));
    }

    @ApiOperation(value = "Update player", response = PlayerResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Player successfully updated"),
            @ApiResponse(code = 400, message = "Player could not be updated"),
            @ApiResponse(code = 404, message = "Player could not be found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public PlayerResponseDto update(@PathVariable("id") Long id, @RequestBody PlayerRequestDto playerRequestDto) {
        return PlayerResponseDtoConverter.convert(playerService.update(id, playerRequestDto));
    }

    @ApiOperation(value = "Delete player")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Player successfully deleted"),
            @ApiResponse(code = 404, message = "Player could not be found")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        playerService.delete(id);
    }

    @Autowired
    private PlayerService playerService;
}
