package com.social.football.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamRequestDto {

    private String name;
    private String currencyCode;

}
