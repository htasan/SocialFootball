package com.social.football.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ContractResponseDto {

    private Long id;
    private String playerName;
    private String teamName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
