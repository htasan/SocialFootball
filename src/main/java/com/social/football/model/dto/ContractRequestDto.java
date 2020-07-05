package com.social.football.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractRequestDto {

    private Long playerId;
    private Long teamId;
    private Integer startYear;
    private Integer endYear;

}
