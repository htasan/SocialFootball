package com.social.football.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamPlayersDto {

    private Long playerId;
    private String playerName;
}
