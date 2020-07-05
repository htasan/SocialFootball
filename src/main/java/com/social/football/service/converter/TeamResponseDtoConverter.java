package com.social.football.service.converter;

import com.social.football.model.Team;
import com.social.football.model.dto.TeamResponseDto;

public class TeamResponseDtoConverter {

    private TeamResponseDtoConverter() {};

    public static TeamResponseDto convert(Team team) {
        return TeamResponseDto.builder().id(team.getId()).name(team.getName()).currencyCode(team.getCurrencyCode()).build();
    }
}
