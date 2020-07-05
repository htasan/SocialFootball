package com.social.football.service.converter;

import com.social.football.model.Contract;
import com.social.football.model.dto.PlayerTeamsDto;
import com.social.football.model.dto.TeamPlayersDto;

import java.util.List;
import java.util.stream.Collectors;

public class ContractDtoListConverter {

    private ContractDtoListConverter() {}

    public static List<PlayerTeamsDto> convertToPlayerTeamsList(List<Contract> contractList) {
        return contractList.stream()
                .map(contract -> PlayerTeamsDto.builder()
                        .yearInfo(contract.getYearInfo())
                        .teamName(contract.getTeam().getName())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<TeamPlayersDto> convertToTeamPlayersList(List<Contract> contractList) {
        return contractList.stream()
                .map(contract -> TeamPlayersDto.builder()
                        .playerId(contract.getPlayer().getId())
                        .playerName(contract.getPlayer().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
