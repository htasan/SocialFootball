package com.social.football.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerTeamsDto {

    private String yearInfo;
    private String teamName;
}
